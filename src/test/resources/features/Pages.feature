#language: en
Feature:
  As a user, i want to ...

  @UiPages
  Scenario:  I want to test UiPages Scenario
     # Test 1-1:
    Given browser: "Chrome"
    And open webpage: "https://www.google.com"
    When search for: "Selenide"
#    And go to endpoint: "/b"
    Then assert that result page contains text: "Selenide: удобные тесты на Selenium WebDriver / Хабрахабр"
    And assert that request element contains text: "Selenide: лаконичные и стабильные UI тесты на Java"
    And click on "3" -rd link
    And wait: "5000" milliseconds
    And close webpage
