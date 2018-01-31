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
    And set login as: "<username>" and password as: "<password>"
    And assert that login error occured
    And chose to register from inside of error message
  Examples:
  | username   | password |
  | testuser_1 | Test@153 |
  | testuser_2 | Test@153 |

    #And wait: 100000 milliseconds
    #And Close popup