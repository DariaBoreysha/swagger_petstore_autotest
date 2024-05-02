# encoding: UTF-8
# language: en

@all @smoke @regress @Find_by_status
Feature: [SWAGGER-1] Поиск питомца по статусу

  @SWAGGER-1.1
  Scenario: Передача одного значения параметра status
    Given формируем GET запрос и отправляем на "https://petstore.swagger.io/v2/pet/findByStatus" с одним параметром и получаем тело JSON
