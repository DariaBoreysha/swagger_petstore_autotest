# encoding: UTF-8
# language: en

@all @smoke @regress @Find_by_status
Feature: [SWAGGER-1] Поиск питомца по статусу

  @SWAGGER-1.1
  Scenario Outline: Передача одного значения параметра status
    When формируем GET запрос с валидным параметром "status" со значением "<status>", отправляем на "https://petstore.swagger.io/v2/pet/findByStatus" и сохраняем ответ как "response_body"
      | header | value            |
      | accept | application/json |
#    Then извлекаем статус код и поясняющую фразу из Memory переменных : "status_code", "reason_phrase" и проверяем их соответствие значениям таблицы
#      | code | phrase |
#      | 200  | OK     |
#    And получаем JSON из Memory переменной : "response_body" и проверяем, что структура тела JSON соответствует JSON schema : "get.json"
#    And парсим JSON из Memory переменной : "response_body" на POJO классы и проверяем, что значение поля status соответствует значению "<status>" из запроса

    Examples:
      | status    |
      | available |
      | pending   |
      | sold      |