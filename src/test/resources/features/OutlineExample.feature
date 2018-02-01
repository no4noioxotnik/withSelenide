#language: en
Feature:
  As a user, i want to ...

  @UiOutline
  Scenario Outline: I have downloaded and parsed wsdl from url, then generate wsdl-based dummy xml file and fill it with
  data by xpath, then send this request to upload document to ftp, check response
     # Test 1-1:
    Given browser: Chrome
    When open webpage: https://www.hackthissite.org/info/about
    Then set login as: "<username>" and password as: "<password>"
    And assert that login error occured

  Examples:
  | username   | password |
  | testuser_1 | Test@153 |
  | testuser_2 | Test@153 |
  | John Dow   | 153      |
  | FuckThisSite | Test@153 |


#    When open webpage: http://google.com
#    Then search for: Hack this
#    And click on 3 -rd link
#    And switch to the previous tab
#    And wait: 1000 milliseconds
#    And choose to register from inside of error message
#    And Close popup