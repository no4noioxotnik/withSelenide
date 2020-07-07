#language: en
Feature:
  As a user, i want to ...

  @Rest-Tests
  Scenario: I want to test Rest-Tests Scenario

#  Given Set url as string: "https://itunes.apple.com" and endpoint: "/search" and request: "?term=doom&country=IL"
  Given Set url as string: "https://itunes.apple.com" and endpoint: "/search" and request: "?term=jack+johnson&limit=25"

  When set body as text: '<string>'
  Then Send rest GET request with body
  And Parse JSON response
  And Assert that JSON response contains key: "resultCount" and int value: "25"

#  And set body as text: '<string>'

#  Given REST client with url: https://itunes.apple.com:22/search?
#    Given REST client with method: <string> and url: http|https://<string>:<number>/<string>
#    And set url path: <string> and save it as url_<string>
#    And set header: <string>: <string>
#    When set body as file: /test_data/test.txt
#    And save MD5 hashsum of the request body
#    When sending request with defined client and expect "string|json|binary"
#    Then assert that response code is "<number>"
#    And assert that response header "<string>" equals|contains "<string>"





