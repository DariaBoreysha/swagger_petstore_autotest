# encoding: UTF-8
# language: en

@all @smoke @regress @Add_new_pet
Feature: [SWAGGER-2] Создание новой записи о питомце

  @SWAGGER-2.1 @positive
  Scenario Outline: Добавление записи о питомце с уникальным id
    And формируем JSON на основе шаблона "addNewPet.json" и сохраняем в Memory как "request_body"
      | field         | value                                |
      | pet_entity_id | GENERATE : pet_entity_id             |
      | category_id   | GENERATE : id                        |
      | category_name | Animal                               |
      | pet_name      | Rocky                                |
      | photourls     | img/test/dog.jpeg,img/test/dog1.jpeg |
      | tag_id        | GENERATE : id                        |
      | tag_name      | Pet                                  |
      | status        | <status>                             |
    When отправляем POST запрос c телом из Memory: "request_body" на "https://petstore.swagger.io/v2/pet" и сохраняем ответ в Memory как "response_entity"
    Then извлекаем ответ из Memory переменной : "response_entity" и проверяем соответствие статус кода и поясняющей фразы значениям <code>, "<phrase>"
#    And извлекаем тело из Memory: "request_body", создаем объект класса "Pet" и сохраняем в Memory как "request_as_pojo"
#    And извлекаем тело из Memory: "response_entity", создаем объект класса "Pet" и сохраняем в Memory как "response_as_pojo"
#    And извлекаем тело ответа и тело запроса из Memory: "request_as_pojo", "response_as_pojo" и проверяем, что ответ и запрос совпадают
#    And отправляем GET запрос на "https://petstore.swagger.io" эндпойнт "/v2/pet/" и сохраняем тело ответа в Memory как "get_response"
#    And извлекаем тело из Memory: "get_response", создаем объект класса "Pet" и сохраняем в Memory как "get_response_as_pojo"
#    And извлекаем тело ответа и тело запроса из Memory: "request_as_pojo", "get_response_as_pojo" и проверяем, что ответ и запрос совпадают

    Examples:
      | status    | code | phrase |
      | available | 200  | OK     |
      | pending   | 200  | OK     |
      | sold      | 200  | OK     |
