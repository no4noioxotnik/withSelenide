#language: en
Feature:
  As a user, i want to ...

  @Ssh-Tests
  Scenario: I want to test Ssh-Tests Scenario

    Given i want to connect via ssh to host "<string>" port "<number>" with username "<string>" and password "<string>"
#    And execute via ssh a command "ping -c 1 google.com"