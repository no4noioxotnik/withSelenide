#language: en
Feature:
  As a user, i want to ...

  @Ui-Tests
  Scenario: I have downloaded and parsed wsdl from url, then generate wsdl-based dummy xml file and fill it with
  data by xpath, then send this request to upload document to ftp, check response
     # Test 1-1:
    Given browser: Chrome
    When open webpage: http://google.com
    Then search for: Hack this
    And click on 3 -rd link
    And switch to the previous tab
    And wait: 1000 milliseconds
    And set login as: Jim Hook and password as: 123
    And assert that login error occured
    And chose to register from inside of error message
    #And wait: 100000 milliseconds
    #And Close popup