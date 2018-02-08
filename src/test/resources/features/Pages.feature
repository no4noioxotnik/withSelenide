#language: en
Feature:
  As a user, i want to ...

  @UiOutline
  Scenario: I want to introduce page objects with Selenide
     # Test 1-1:
    Given browser: "Chrome"
    And open webpage: "https://www.google.com"
#    And go to endpoint: "/b"
    When search for: "Selenide"
    Then assert that result page contains text: "Selenide: удобные тесты на Selenium WebDriver / Хабрахабр"
    And assert that request element contains text: "Selenide: лаконичные и стабильные UI тесты на Java"
