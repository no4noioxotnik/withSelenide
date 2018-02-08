#language: en
Feature:
  As a user, i want to ...

  @Ui-Tests
  Scenario: i want to test my new methods

#  Given create sparse file to path "src/test/resources/test_data/" with size "2565" and filename "testoo.txt"
  Given compare file with pathname "src/test/resources/test_data/testo.txt" to file "src/test/resources/test_data/testoo.txt"

