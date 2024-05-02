# encoding: UTF-8
# language: en

@all @smoke @regress @Find_by_status
Feature: [SWAGGER-1] Поиск питомца по статусу

  @SWAGGER-1.1
  Scenario: Передача 1 значения параметра status
    Given формируем GET запрос и отправляем на "https://petstore.swagger.io/v2/pet/findByStatus" со следующим параметром:
      | param  | value     |
      | status | available |