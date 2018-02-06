#language: en
Feature:
  As a user, i want to ...

  @Ui-Tests
  Scenario: i've got new methods to test

    Given Soap client with endpointURI: https://db.1c-ksu.ru/UH_Test_Zolotarev/ws/ReportServiceFZPG username: UserReportServiceFZPG and password s1CeB5Ams
    And Wsdl with url: https://db.1c-ksu.ru/UH_Test_Zolotarev/ws/ReportServiceFZPG?wsdl
    When I get wsdl, I want to get a new empty request for the operation
    And set namespace: c to value: http://1c.ru
    And set namespace: rep to value: http://1c-ksu.ru/reportservice
    #And set SOAP header: SOAPAction to value: "http://schemas.microsoft.com/sqlserver/2004/SOAPsqlbatch"
    And set XML body via any xpath: //rep:RequestID to value: timestamp
    And set XML body via any xpath: //rep:SystemID to value: a31c9c35-56a0-47dc-80bc-79805f5625f6
    And set XML body via any xpath: //rep:ReportID to value: АнализДСЗастройщиковНаРСФонда
    And set XML body via any xpath: //rep:StartDate to value: 2017-01-01T07:50:00Z
    And set XML body via any xpath: //rep:EndDate to value: 2017-11-09T07:50:00Z
    Then send modified message to host service
    And I want to assert that the text contains SUCCESS in response
    And I want to assert that response statusCode is 200
    And I want to assert that response is correct
    And setup ftp mock server set port: 2221 username: user password: password homeDir: src/test/resources/test_data/
    And Set timeout 10000 milliseconds
    And get host IP address


#    Given Soap client with endpointURI: <string> username: <string> and password <string>
#    And Wsdl with url: https://<string>
#    When I get wsdl, I want to get a new empty request for the operation
#    And set namespace: <string> to value: <string>
#    #And set SOAP header: SOAPAction to value: "http://schemas.microsoft.com/sqlserver/2004/SOAPsqlbatch"
#    And set XML body via any xpath: //<string> to value: timestamp
#    Then send modified message to host service
#    And I want to assert that the text contains SUCCESS in response
#    And I want to assert that response statusCode is 200
#    And I want to assert that response is correct
#    And setup ftp mock server set port: 2221 username: user password: password homeDir: src/test/resources/test_data/
#    And Set timeout 10000 milliseconds
#    And get host IP address