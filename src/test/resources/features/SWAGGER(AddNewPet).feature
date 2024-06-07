# encoding: UTF-8
# language: en

@all @smoke @regress @Add_new_pet
Feature: [SWAGGER-2] Создание новой записи о питомце

#  @SWAGGER-2.1 @positive
#  Scenario Outline: Добавление записи о питомце с уникальным id
#    Given отправляем DELETE запрос на "https://petstore.swagger.io" эндпойнт "/v2/pet/" с path параметром "pet_entity_id" и сохраняем ответ в Memory как "delete_body"
#      | variable      | value                    |
#      | pet_entity_id | GENERATE : pet_entity_id |
#    And формируем JSON на основе шаблона "addNewPet.json" и сохраняем в Memory как "request_body"
#      | field         | value                                |
#      | pet_entity_id | MEMORY : pet_entity_id               |
#      | category_id   | GENERATE : id                        |
#      | category_name | Animal                               |
#      | pet_name      | Rocky                                |
#      | photourls     | img/test/dog.jpeg,img/test/dog1.jpeg |
#      | tag_id        | GENERATE : id                        |
#      | tag_name      | Pet                                  |
#      | status        | <status>                             |
#    When отправляем POST запрос c телом из Memory: "request_body" на "https://petstore.swagger.io/v2/pet" и сохраняем ответ в Memory как "response_entity"
#    Then извлекаем ответ из Memory переменной : "response_entity" и проверяем соответствие статус кода и поясняющей фразы значениям <code>, "<phrase>"
#    And извлекаем тело ответа из Memory: "response_entity", конвертируем в jsonNode и сохраняем в Memory как "response_as_jsonNode"
#    And извлекаем тело запроса из Memory: "request_body", конвертируем в jsonNode и сохраняем в Memory как "request_as_jsonNode"
#    And проверяем, что JsonNode из Memory: "response_as_jsonNode" соответствует  JsonNode из Memory: "request_as_jsonNode"
#    And отправляем GET запрос на "https://petstore.swagger.io" эндпойнт "/v2/pet/" с path параметром "pet_entity_id" и сохраняем тело ответа в Memory как "get_response"
#    And извлекаем тело ответа из Memory: "get_response", конвертируем в jsonNode и сохраняем в Memory как "get_response_as_jsonNode"
#    And проверяем, что JsonNode из Memory: "get_response_as_jsonNode" соответствует  JsonNode из Memory: "request_as_jsonNode"
#
#    Examples:
#      | status    | code | phrase |
#      | available | 200  | OK     |
#      | pending   | 200  | OK     |
#      | sold      | 200  | OK     |

  @SWAGGER-2.2 @positive
  Scenario Outline: Обновление записи о питомце при отправке POST запроса на неуникальный id
    Given формируем JSON на основе шаблона "addNewPet.json" и сохраняем в Memory как "request_body"
      | field         | value                                |
      | pet_entity_id | GENERATE : pet_entity_id             |
      | category_id   | GENERATE : id                        |
      | category_name | Animal                               |
      | pet_name      | Rocky                                |
      | photourls     | img/test/dog.jpeg,img/test/dog1.jpeg |
      | tag_id        | GENERATE : id                        |
      | tag_name      | Pet                                  |
      | status        | <status>                             |
    And отправляем POST запрос c телом из Memory: "request_body" на "https://petstore.swagger.io/v2/pet" и сохраняем ответ в Memory как "response_entity"
    And формируем JSON на основе шаблона "addNewPet.json" и сохраняем в Memory как "request_body"
      | field         | value                                |
      | pet_entity_id | MEMORY : pet_entity_id               |
      | category_id   | GENERATE : id                        |
      | category_name | Animal                               |
      | pet_name      | Rocky                                |
      | photourls     | img/test/dog.jpeg,img/test/dog1.jpeg |
      | tag_id        | GENERATE : id                        |
      | tag_name      | Pet                                  |
      | status        | <status>                             |
    When отправляем POST запрос c телом из Memory: "request_body" на "https://petstore.swagger.io/v2/pet" и сохраняем ответ в Memory как "response_entity"
    Then извлекаем ответ из Memory переменной : "response_entity" и проверяем соответствие статус кода и поясняющей фразы значениям <code>, "<phrase>"
    And извлекаем тело ответа из Memory: "response_entity", конвертируем в jsonNode и сохраняем в Memory как "response_as_jsonNode"
    And извлекаем тело запроса из Memory: "request_body", конвертируем в jsonNode и сохраняем в Memory как "request_as_jsonNode"
    And проверяем, что JsonNode из Memory: "response_as_jsonNode" соответствует  JsonNode из Memory: "request_as_jsonNode"
    And отправляем GET запрос на "https://petstore.swagger.io" эндпойнт "/v2/pet/" с path параметром "pet_entity_id" и сохраняем тело ответа в Memory как "get_response"
    And извлекаем тело ответа из Memory: "get_response", конвертируем в jsonNode и сохраняем в Memory как "get_response_as_jsonNode"
    And проверяем, что JsonNode из Memory: "get_response_as_jsonNode" соответствует  JsonNode из Memory: "request_as_jsonNode"

    Examples:
      | status    | code | phrase |
      | available | 200  | OK     |
      | pending   | 200  | OK     |
      | sold      | 200  | OK     |