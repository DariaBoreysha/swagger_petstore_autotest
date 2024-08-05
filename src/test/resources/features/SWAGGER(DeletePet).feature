# encoding: UTF-8
# language: en

@all @smoke @regress @Delete_pet
Feature: [SWAGGER-3] Удаление записи о питомце

  @SWAGGER-3.1 @positive
  Scenario Outline: Удаление записи о существующем питомце
    Given формируем JSON на основе шаблона "addNewPet.json" и сохраняем в Memory как "request_body"
      | field         | value                    |
      | pet_entity_id | GENERATE : pet_entity_id |
      | category_id   | GENERATE : id            |
      | category_name | GENERATE : category_name |
      | pet_name      | GENERATE : pet_name      |
      | photourls     | GENERATE : photourls     |
      | tag_id        | GENERATE : id            |
      | tag_name      | GENERATE : tag_name      |
      | status        | available                |
    And отправляем POST запрос c телом из Memory: "request_body" на "https://petstore.swagger.io/v2/pet" и сохраняем ответ в Memory как "response_entity"
    When отправляем DELETE запрос на "https://petstore.swagger.io" эндпойнт "/v2/pet/" c path параметром "pet_entity_id" и сохраняем ответ в Memory как "response_entity"
      | variable      | value                  |
      | pet_entity_id | MEMORY : pet_entity_id |
    Then извлекаем ответ из Memory переменной : "response_entity" и проверяем соответствие статус кода и поясняющей фразы значениям <code>, "<phrase>"
    And конвертируем ответ из Memory: "response_entity" в JsonNode и сохраняем как "json_node"
    And извлекаем тело JSON из Memory переменной : "json_node" и проверяем соответствие фактических значений полей ожидаемым
      | field   | value                  |
      | code    | 200                    |
      | message | MEMORY : pet_entity_id |
      | type    | unknown                |
    And отправляем GET запрос на "https://petstore.swagger.io" эндпойнт "/v2/pet" с path параметром "pet_entity_id" и сохраняем тело ответа в Memory как "get_response"
    And извлекаем ответ из Memory переменной : "get_response" и проверяем соответствие статус кода и поясняющей фразы значениям <codeGetCheck>, "<phraseGetCheck>"

    Examples:
      | code | phrase | codeGetCheck | phraseGetCheck |
      | 200  | OK     | 404          | Not Found      |

  @SWAGGER-3.2 @negative
  Scenario Outline: Удаление записи о несуществующем питомце
    Given отправляем DELETE запрос на "https://petstore.swagger.io" эндпойнт "/v2/pet/" c path параметром "pet_entity_id" и сохраняем ответ в Memory как "delete_body"
      | variable      | value                    |
      | pet_entity_id | GENERATE : pet_entity_id |
    When отправляем DELETE запрос на "https://petstore.swagger.io" эндпойнт "/v2/pet/" c path параметром "pet_entity_id" и сохраняем ответ в Memory как "response_entity"
      | variable      | value                  |
      | pet_entity_id | MEMORY : pet_entity_id |
    Then извлекаем ответ из Memory переменной : "response_entity" и проверяем соответствие статус кода и поясняющей фразы значениям <code>, "<phrase>"

    Examples:
      | code | phrase    |
      | 404  | Not Found |

  @SWAGGER-3.3 @negative
  Scenario Outline: Заполнение path параметра petId невалидным значением
    When отправляем DELETE запрос на "https://petstore.swagger.io" эндпойнт "/v2/pet/" c path параметром "pet_entity_id" и сохраняем ответ в Memory как "response_entity"
      | variable      | value   |
      | pet_entity_id | <value> |
    Then извлекаем ответ из Memory переменной : "response_entity" и проверяем соответствие статус кода и поясняющей фразы значениям <code>, "<phrase>"
    And конвертируем ответ из Memory: "response_entity" в JsonNode и сохраняем как "json_node"
    And извлекаем тело JSON из Memory переменной : "json_node" и проверяем соответствие фактических значений полей ожидаемым
      | field   | value    |
      | code    | <code>   |
      | message | <phrase> |
      | type    | unknown  |

    Examples:
      | code | phrase              | value                            |
      | 400  | Invalid ID supplied |                                  |
      | 400  | Invalid ID supplied | GENERATE : invalid_pet_entity_id |