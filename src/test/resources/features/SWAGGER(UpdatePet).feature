# encoding: UTF-8
# language: en

@all @smoke @regress @Update_pet
Feature: [SWAGGER-5] Обновление информации о питомце

  @SWAGGER-5.1 @positive
  Scenario Outline: Обновление информации о существующем питомце
    Given формируем JSON на основе шаблона "addNewPet.json" и сохраняем в Memory как "request_body"
      | field         | value                    |
      | pet_entity_id | GENERATE : pet_entity_id |
      | category_id   | GENERATE : id            |
      | category_name | GENERATE : category_name |
      | pet_name      | GENERATE : pet_name      |
      | photourls     | GENERATE : photourls     |
      | tag_id        | GENERATE : id            |
      | tag_name      | GENERATE : tag_name      |
      | status        | <status>                 |
    And отправляем POST запрос c телом из Memory: "request_body" на "https://petstore.swagger.io/v2/pet" и сохраняем ответ в Memory как "response_entity"
    And формируем JSON на основе шаблона "addNewPet.json" и сохраняем в Memory как "request_body"
      | field         | value                    |
      | pet_entity_id | MEMORY : pet_entity_id   |
      | category_id   | GENERATE : id            |
      | category_name | GENERATE : category_name |
      | pet_name      | GENERATE : pet_name      |
      | photourls     | GENERATE : photourls     |
      | tag_id        | GENERATE : id            |
      | tag_name      | GENERATE : tag_name      |
      | status        | <status>                 |
    When отправляем PUT запрос c телом из Memory: "request_body" на "https://petstore.swagger.io/v2/pet" и сохраняем ответ в Memory как "response_entity"
    Then извлекаем ответ из Memory переменной : "response_entity" и проверяем соответствие статус кода и поясняющей фразы значениям <code>, "<phrase>"
    And извлекаем тело ответа из Memory: "response_entity", конвертируем в jsonNode и сохраняем в Memory как "response_as_jsonNode"
    And извлекаем тело запроса из Memory: "request_body", конвертируем в jsonNode и сохраняем в Memory как "request_as_jsonNode"
    And проверяем, что JsonNode из Memory: "response_as_jsonNode" соответствует JsonNode из Memory: "request_as_jsonNode"
    And отправляем GET запрос на "https://petstore.swagger.io" эндпойнт "/v2/pet/" с path параметром "pet_entity_id" и сохраняем тело ответа в Memory как "get_response"
    And извлекаем тело ответа из Memory: "get_response", конвертируем в jsonNode и сохраняем в Memory как "get_response_as_jsonNode"
    And проверяем, что JsonNode из Memory: "get_response_as_jsonNode" соответствует JsonNode из Memory: "request_as_jsonNode"

    Examples:
      | status    | code | phrase |
      | available | 200  | OK     |
      | pending   | 200  | OK     |
      | sold      | 200  | OK     |

  @SWAGGER-5.2 @negative
  Scenario Outline: Обновление информации о несуществующем питомце
    Given отправляем DELETE запрос на "https://petstore.swagger.io" эндпойнт "/v2/pet/" и сохраняем ответ в Memory как "request_body"
      | variable      | value                    |
      | pet_entity_id | GENERATE : pet_entity_id |
    And формируем JSON на основе шаблона "addNewPet.json" и сохраняем в Memory как "request_body"
      | field         | value                    |
      | pet_entity_id | MEMORY : pet_entity_id   |
      | category_id   | GENERATE : id            |
      | category_name | GENERATE : category_name |
      | pet_name      | GENERATE : pet_name      |
      | photourls     | GENERATE : photourls     |
      | tag_id        | GENERATE : id            |
      | tag_name      | GENERATE : tag_name      |
      | status        | available                |
    When отправляем PUT запрос c телом из Memory: "request_body" на "https://petstore.swagger.io/v2/pet" и сохраняем ответ в Memory как "response_entity"
    Then извлекаем ответ из Memory переменной : "response_entity" и проверяем соответствие статус кода и поясняющей фразы значениям <code>, "<phrase>"
    And извлекаем тело ответа из Memory: "response_entity", конвертируем в jsonNode и сохраняем в Memory как "json_node"
    And извлекаем тело JSON из Memory переменной : "json_node" и проверяем соответствие фактических значений полей ожидаемым
      | field   | value    |
      | code    | <code>   |
      | message | <phrase> |
      | type    | unknown  |

    Examples:
      | code | phrase        |
      | 404  | Pet not found |

  @SWAGGER-5.3 @negative
  Scenario Outline: Обновление информации о питомце c незаполненными обязательными полями
    Given формируем JSON на основе шаблона "addNewPet.json" и сохраняем в Memory как "request_body"
      | field         | value                    |
      | pet_entity_id | GENERATE : pet_entity_id |
      | category_id   | GENERATE : id            |
      | category_name | GENERATE : category_name |
      | pet_name      | GENERATE : pet_name      |
      | photourls     | GENERATE : photourls     |
      | tag_id        | GENERATE : id            |
      | tag_name      | GENERATE : tag_name      |
      | status        | <status>                 |
    And отправляем POST запрос c телом из Memory: "request_body" на "https://petstore.swagger.io/v2/pet" и сохраняем ответ в Memory как "response_entity"
    And формируем JSON на основе шаблона "addNewPet.json" и сохраняем в Memory как "request_body"
      | field         | value                    |
      | pet_entity_id | MEMORY : pet_entity_id   |
      | category_id   | GENERATE : id            |
      | category_name | GENERATE : category_name |
      | pet_name      | null                     |
      | photourls     | null                     |
      | tag_id        | GENERATE : id            |
      | tag_name      | GENERATE : tag_name      |
      | status        | available                |
    When отправляем PUT запрос c телом из Memory: "request_body" на "https://petstore.swagger.io/v2/pet" и сохраняем ответ в Memory как "response_entity"
    Then извлекаем ответ из Memory переменной : "response_entity" и проверяем соответствие статус кода и поясняющей фразы значениям <code>, "<phrase>"
    And извлекаем тело ответа из Memory: "response_entity", конвертируем в jsonNode и сохраняем в Memory как "json_node"
    And извлекаем тело JSON из Memory переменной : "json_node" и проверяем соответствие фактических значений полей ожидаемым
      | field   | value    |
      | code    | <code>   |
      | message | <phrase> |
      | type    | unknown  |

    Examples:
      | code | phrase               |
      | 405  | Validation exception |