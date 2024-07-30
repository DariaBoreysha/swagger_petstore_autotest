# encoding: UTF-8
# language: en

@all @smoke @regress @Make_order_for_pet
Feature: [SWAGGER-4] Создание нового заказа

  @SWAGGER-4.1 @positive
  Scenario Outline: Добавление записи о заказе с уникальным id
    Given отправляем DELETE запрос на "https://petstore.swagger.io" эндпойнт "/v2/store/order/" и сохраняем ответ в Memory как "delete_body"
      | variable      | value               |
      | pet_entity_id | GENERATE : order_id |
    And формируем JSON на основе шаблона "makeOrderForPet.json" и сохраняем в Memory как "request_body"
      | field         | value                    |
      | order_id      | MEMORY : order_id        |
      | pet_entity_id | GENERATE : pet_entity_id |
      | quantity      | GENERATE : quantity      |
      | ship_date     | GENERATE : date          |
      | status        | <status>                 |
      | complete      | true                     |
    When отправляем POST запрос c телом из Memory: "request_body" на "https://petstore.swagger.io/v2/store/order" и сохраняем ответ в Memory как "response_entity"
    Then извлекаем ответ из Memory переменной : "response_entity" и проверяем соответствие статус кода и поясняющей фразы значениям <code>, "<phrase>"
    And извлекаем тело ответа из Memory: "response_entity", конвертируем в jsonNode и сохраняем в Memory как "response_as_jsonNode"
    And извлекаем тело запроса из Memory: "request_body", конвертируем в jsonNode и сохраняем в Memory как "request_as_jsonNode"
    And проверяем, что JsonNode из Memory: "response_as_jsonNode" соответствует JsonNode из Memory: "request_as_jsonNode"
    And отправляем GET запрос на "https://petstore.swagger.io" эндпойнт "/v2/store/order/" с path параметром "order_id" и сохраняем тело ответа в Memory как "get_response"
    And извлекаем тело ответа из Memory: "get_response", конвертируем в jsonNode и сохраняем в Memory как "get_response_as_jsonNode"
    And проверяем, что JsonNode из Memory: "get_response_as_jsonNode" соответствует JsonNode из Memory: "request_as_jsonNode"

    Examples:
      | status    | code | phrase |
      | placed    | 200  | OK     |
      | approved  | 200  | OK     |
      | delivered | 200  | OK     |

  @SWAGGER-4.2 @negative
  Scenario Outline: Добавление записи о заказе с незаполненными полями
    Given формируем JSON на основе шаблона "makeOrderForPet.json" и сохраняем в Memory как "request_body"
      | field         | value |
      | order_id      | null  |
      | pet_entity_id | null  |
      | quantity      | null  |
      | ship_date     | null  |
      | status        | null  |
      | complete      | null  |
    When отправляем POST запрос c телом из Memory: "request_body" на "https://petstore.swagger.io/v2/store/order" и сохраняем ответ в Memory как "response_entity"
    Then извлекаем ответ из Memory переменной : "response_entity" и проверяем соответствие статус кода и поясняющей фразы значениям <code>, "<phrase>"
    And извлекаем тело ответа из Memory: "response_entity", конвертируем в jsonNode и сохраняем в Memory как "response_as_jsonNode"
    And конвертируем ожидаемый ответ сервиса "NullReqMakeOrderForPetRes.json" в jsonNode и сохраняем в Memory как "expected_jsonNode"
    And проверяем, что JsonNode из Memory: "response_as_jsonNode" соответствует JsonNode из Memory: "expected_jsonNode"

    Examples:
      | code | phrase |
      | 200  | OK     |

  @SWAGGER-4.3 @positive
  Scenario Outline: Добавление записи о заказе с неуникальным id
    Given формируем JSON на основе шаблона "makeOrderForPet.json" и сохраняем в Memory как "request_body"
      | field         | value                    |
      | order_id      | GENERATE : order_id      |
      | pet_entity_id | GENERATE : pet_entity_id |
      | quantity      | GENERATE : quantity      |
      | ship_date     | GENERATE : date          |
      | status        | <status>                 |
      | complete      | true                     |
    When отправляем POST запрос c телом из Memory: "request_body" на "https://petstore.swagger.io/v2/store/order" и сохраняем ответ в Memory как "response_entity"
    And формируем JSON на основе шаблона "makeOrderForPet.json" и сохраняем в Memory как "request_body"
      | field         | value                    |
      | order_id      | MEMORY : order_id        |
      | pet_entity_id | GENERATE : pet_entity_id |
      | quantity      | GENERATE : quantity      |
      | ship_date     | GENERATE : date          |
      | status        | <status>                 |
      | complete      | false                     |
    When отправляем POST запрос c телом из Memory: "request_body" на "https://petstore.swagger.io/v2/store/order" и сохраняем ответ в Memory как "response_entity"
    Then извлекаем ответ из Memory переменной : "response_entity" и проверяем соответствие статус кода и поясняющей фразы значениям <code>, "<phrase>"
    And извлекаем тело ответа из Memory: "response_entity", конвертируем в jsonNode и сохраняем в Memory как "response_as_jsonNode"
    And извлекаем тело запроса из Memory: "request_body", конвертируем в jsonNode и сохраняем в Memory как "request_as_jsonNode"
    And проверяем, что JsonNode из Memory: "response_as_jsonNode" соответствует JsonNode из Memory: "request_as_jsonNode"
    And отправляем GET запрос на "https://petstore.swagger.io" эндпойнт "/v2/store/order/" с path параметром "order_id" и сохраняем тело ответа в Memory как "get_response"
    And извлекаем тело ответа из Memory: "get_response", конвертируем в jsonNode и сохраняем в Memory как "get_response_as_jsonNode"
    And проверяем, что JsonNode из Memory: "get_response_as_jsonNode" соответствует JsonNode из Memory: "request_as_jsonNode"

    Examples:
      | status    | code | phrase |
      | placed    | 200  | OK     |
      | approved  | 200  | OK     |
      | delivered | 200  | OK     |


  @SWAGGER-4.4 @negative
  Scenario Outline: Превышение максимального валидного значения поля order_id
    Given формируем JSON на основе шаблона "makeOrderForPet.json" и сохраняем в Memory как "request_body"
      | field         | value                    |
      | order_id      | GENERATE : invalid_id    |
      | pet_entity_id | GENERATE : pet_entity_id |
      | quantity      | GENERATE : quantity      |
      | ship_date     | GENERATE : date          |
      | status        | <status>                 |
      | complete      | true                     |
    When отправляем POST запрос c телом из Memory: "request_body" на "https://petstore.swagger.io/v2/store/order" и сохраняем ответ в Memory как "response_entity"
    Then извлекаем ответ из Memory переменной : "response_entity" и проверяем соответствие статус кода и поясняющей фразы значениям <code>, "<phrase>"
    And конвертируем ответ из Memory: "response_entity" в JsonNode и сохраняем как "json_node"
    And извлекаем тело JSON из Memory переменной : "json_node" и проверяем соответствие фактических значений полей ожидаемым
      | field   | value                  |
      | code    | <code>                 |
      | message | something bad happened |
      | type    | unknown                |

    Examples:
      | status    | code | phrase       |
      | placed    | 500  | Server Error |
      | approved  | 500  | Server Error |
      | delivered | 500  | Server Error |

  @SWAGGER-4.5 @negative
  Scenario Outline: Использование некорректного значения поля status
    Given формируем JSON на основе шаблона "makeOrderForPet.json" и сохраняем в Memory как "request_body"
      | field         | value                    |
      | order_id      | GENERATE : order_id      |
      | pet_entity_id | GENERATE : pet_entity_id |
      | quantity      | GENERATE : quantity      |
      | ship_date     | GENERATE : date          |
      | status        | <status>                 |
      | complete      | true                     |
    When отправляем POST запрос c телом из Memory: "request_body" на "https://petstore.swagger.io/v2/store/order" и сохраняем ответ в Memory как "response_entity"
    Then извлекаем ответ из Memory переменной : "response_entity" и проверяем соответствие статус кода и поясняющей фразы значениям <code>, "<phrase>"
    And конвертируем ответ из Memory: "response_entity" в JsonNode и сохраняем как "json_node"
    And извлекаем тело JSON из Memory переменной : "json_node" и проверяем соответствие фактических значений полей ожидаемым
      | field   | value    |
      | code    | <code>   |
      | message | <phrase> |
      | type    | unknown  |

    Examples:
      | status    | code | phrase        |
      | available | 400  | Invalid Order |
      | null      | 400  | Invalid Order |
