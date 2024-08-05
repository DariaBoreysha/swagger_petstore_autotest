# encoding: UTF-8
# language: en

@all @smoke @regress @Delete_pet_order
Feature: [SWAGGER-6] Удаление записи о заказе

  @SWAGGER-6.1 @positive
  Scenario Outline: Удаление записи о существующем заказе
    Given формируем JSON на основе шаблона "makeOrderForPet.json" и сохраняем в Memory как "request_body"
      | field         | value                    |
      | order_id      | GENERATE : order_id      |
      | pet_entity_id | GENERATE : pet_entity_id |
      | quantity      | GENERATE : quantity      |
      | ship_date     | GENERATE : date          |
      | status        | placed                   |
      | complete      | true                     |
    And отправляем POST запрос c телом из Memory: "request_body" на "https://petstore.swagger.io/v2/store/order/" и сохраняем ответ в Memory как "response_entity"
    When отправляем DELETE запрос на "https://petstore.swagger.io" эндпойнт "/v2/store/order/" c path параметром "order_id" и сохраняем ответ в Memory как "response_entity"
      | variable | value             |
      | order_id | MEMORY : order_id |
    Then извлекаем ответ из Memory переменной : "response_entity" и проверяем соответствие статус кода и поясняющей фразы значениям <code>, "<phrase>"
    And конвертируем ответ из Memory: "response_entity" в JsonNode и сохраняем как "json_node"
    And извлекаем тело JSON из Memory переменной : "json_node" и проверяем соответствие фактических значений полей ожидаемым
      | field   | value             |
      | code    | 200               |
      | message | MEMORY : order_id |
      | type    | unknown           |
    When отправляем DELETE запрос на "https://petstore.swagger.io" эндпойнт "/v2/store/order/" c path параметром "order_id" и сохраняем ответ в Memory как "response_entity"
      | variable | value             |
      | order_id | MEMORY : order_id |
    And конвертируем ответ из Memory: "response_entity" в JsonNode и сохраняем как "json_node"
    And извлекаем тело JSON из Memory переменной : "json_node" и проверяем соответствие фактических значений полей ожидаемым
      | field   | value           |
      | code    | 404             |
      | message | Order Not Found |
      | type    | unknown         |

    Examples:
      | code | phrase |
      | 200  | OK     |

  @SWAGGER-6.2 @negative
  Scenario Outline: Заполнение path параметра orderId невалидным значением
    When отправляем DELETE запрос на "https://petstore.swagger.io" эндпойнт "/v2/store/order/" c path параметром "order_id" и сохраняем ответ в Memory как "response_entity"
      | variable | value   |
      | order_id | <value> |
    Then извлекаем ответ из Memory переменной : "response_entity" и проверяем соответствие статус кода и поясняющей фразы значениям <code>, "<phrase>"
    And конвертируем ответ из Memory: "response_entity" в JsonNode и сохраняем как "json_node"
    And извлекаем тело JSON из Memory переменной : "json_node" и проверяем соответствие фактических значений полей ожидаемым
      | field   | value    |
      | code    | <code>   |
      | message | <phrase> |
      | type    | unknown  |

    Examples:
      | code | phrase              | value                       |
      | 400  | Invalid ID supplied |                             |
      | 400  | Invalid ID supplied | GENERATE : invalid_order_id |
