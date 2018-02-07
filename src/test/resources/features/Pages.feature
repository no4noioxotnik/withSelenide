#language: en
Feature:
  As a user, i want to ...

  @UiOutline
  Scenario: I want to introduce page objects with Selenide
     # Test 1-1:
    Given browser: Chrome
    And get host IP address
    And open webpage: https://www.google.com
    When search for: Selenide
#    When go to page: /login
    Then assert that result page contains text: Selenide: удобные тесты на Selenium WebDriver / Хабрахабр