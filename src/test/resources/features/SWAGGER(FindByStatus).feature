# encoding: UTF-8
# language: en

@all @smoke @regress @Find_by_status
Feature: [SWAGGER-1] Поиск питомца по статусу

  @SWAGGER-1.1
  Scenario Outline: Передача одного значения параметра status
    When формируем GET запрос и отправляем на "https://petstore.swagger.io/v2/pet/findByStatus" с одним параметром "<status>" и получаем тело JSON
      | header | value            |
      | accept | application/json |

    Examples:
      | status    |
      | available |
      | pending   |
      | sold      |