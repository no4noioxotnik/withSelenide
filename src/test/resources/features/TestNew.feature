#language: en
Feature:
  As a user, i want to ...

  @TestNew
  Scenario: I have downloaded and parsed wsdl from url, then generate wsdl-based dummy xml file and fill it with
  data by xpath, then send this request to upload document to ftp, check response
     # Test 1-1:
    Given  get host IP address
    #When delete wsdl file parameters.wsdl