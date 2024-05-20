# encoding: UTF-8
# language: en

@all @smoke @regress @Find_by_status
Feature: [SWAGGER-1] Поиск питомца по статусу

  @SWAGGER-1.1 @positive
  Scenario Outline: Передача валидного значения параметра status
    When формируем GET запрос с параметром "status" со значением "<status>", отправляем на "https://petstore.swagger.io/v2/pet/findByStatus" и сохраняем ответ в Memory как "response_entity"
    Then извлекаем ответ из Memory переменной : "response_entity" и проверяем соответствие статус кода и поясняющей фразы значениям <code>, "<phrase>"
    And конвертируем ответ из Memory: "response_entity" в JsonNode и сохраняем как "json_node"
    And извлекаем тело JSON из Memory переменной : "json_node" и проверяем, что значение поля status соответствует значению "<status>" запроса
    And извлекаем тело JSON из Memory переменной : "json_node" и проверяем, что структура тела JSON соответствует JSON schema : "get.json"

    Examples:
      | status                 | code | phrase |
      | available              | 200  | OK     |
      | pending                | 200  | OK     |
      | sold                   | 200  | OK     |
      | available,pending,sold | 200  | OK     |
      | pending,sold           | 200  | OK     |

  @SWAGGER-1.2 @negative
  Scenario Outline: Передача некорректного значения параметра status
    When формируем GET запрос с параметром "status" со значением "<status>", отправляем на "https://petstore.swagger.io/v2/pet/findByStatus" и сохраняем ответ в Memory как "response_entity"
    Then извлекаем ответ из Memory переменной : "response_entity" и проверяем соответствие статус кода и поясняющей фразы значениям <code>, "<phrase>"
    And конвертируем ответ из Memory: "response_entity" в JsonNode и сохраняем как "json_node"
    And извлекаем тело JSON из Memory переменной : "json_node" и проверяем соответствие полей code, type, message значениям <code>, "<type>", "<message>"

    Examples:
      | status  | code | phrase         | type    | message        |
      | created | 400  | Invalid status | unknown | Invalid status |
      |         | 400  | Invalid status | unknown | Invalid status |