# encoding: UTF-8
# language: en

@all @smoke @regress @Find_by_status
Feature: [SWAGGER-1] Поиск питомца по статусу

  @SWAGGER-1.1 @positive
  Scenario Outline: Передача валидного значения параметра status
    When формируем GET запрос с валидным параметром "status" со значением "<status>", отправляем на "https://petstore.swagger.io/v2/pet/findByStatus" и сохраняем ответ в Memory как "response_body"
#    Then извлекаем ответ из Memory переменной : "response_body" и проверяем соответствие статус кода и поясняющей фразы значениям "<code>", "<phrase>"
#    And извлекаем ответ из Memory переменной : "response_body" и проверяем, что структура тела JSON соответствует JSON schema : "get.json"
#    And парсим JSON из Memory переменной : "response_body" на POJO классы и проверяем, что значение поля status соответствует значению "<status>" из запроса

    Examples:
      | status                 | code | phrase |
      | available              | 200  | OK     |
      | pending                | 200  | OK     |
      | sold                   | 200  | OK     |
      | available,pending,sold | 200  | OK     |
      | pending,sold           | 200  | OK     |