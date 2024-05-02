# encoding: UTF-8
# language: en

@all @smoke @regress @Find_by_status
Feature: [SWAGGER-1] Поиск питомца по статусу

  @SWAGGER-1.1
  Scenario: Передача 1 значения параметра status
    Given формируем JSON с полями для вида расчёта "PD_SB" на основе шаблона "default.json" для POST запроса в сервис OW и сохраняем в Memory как "request_body"
      | field                       | value                       |
      | app_id                      | GENERATE : app_id           |
      | request_id                  | GENERATE : request_id       |
      | tin                         | GENERATE : inn              |
      | scoring_stage               | ["F","K"]                   |
      | presence_of_consent_company | CONSENT_FOUND               |
      | questionnaire               | default.xml                 |
      | credit_history_company      | default.xml                 |
      | credit_history_individual   | default.xml                 |
    When подготовленный POST запрос из 'MEMORY : "request_body"' отправляем на "/ufr-ow-potok-api/pd_sb" и сохраняем ответ в Memory как "response_body"
      | header                      | value                           |
      | content-type                | application/json                |
    Then проверяем значения полей в JSON ответе из 'MEMORY : "response_body"'
      | field                       | value                           |
      | caller_id                   | WSRM                            |
      | external_app_id             | MEMORY : app_id                 |
      | external_request_id         | MEMORY : request_id             |
    And сохраняем поле "internal_request_id" из полученного синхронного JSON ответа из 'MEMORY : "response_body"' в Memory как "internal_request_id"
    And ждём до 20 секунд появления в таблице "REQUESTS" записи с полями:
      | field                       | value                           |
      | request_id                  | MEMORY : internal_request_id    |