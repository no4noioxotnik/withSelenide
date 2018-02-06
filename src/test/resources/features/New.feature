#language: en
Feature:
  As a user, i want to ...

  @Ui-Tests
  Scenario: i've got new methods to test

    Given Soap client with endpointURI: <string> username: <string> and password <string>
    And Wsdl with url: https://<string>
    When I get wsdl, I want to get a new empty request for the operation
    And set namespace: <string> to value: <string>
    #And set SOAP header: SOAPAction to value: "http://schemas.microsoft.com/sqlserver/2004/SOAPsqlbatch"
    And set XML body via any xpath: //<string> to value: timestamp
    Then send modified message to host service
    And I want to assert that the text contains SUCCESS in response
    And I want to assert that response statusCode is 200
    And I want to assert that response is correct
    And setup ftp mock server set port: 2221 username: user password: password homeDir: src/test/resources/test_data/
    And Set timeout 10000 milliseconds
    And get host IP address