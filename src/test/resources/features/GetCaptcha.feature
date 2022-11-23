Feature: Get captcha
  I want to get captcha web by automation

  @1
  Scenario: Get captcha and enter captcha on textbox
    Given Open login page
    When Input captcha on textbox
    And Verify the captcha input is right
    Then Close page

  @2
  Scenario: Click on button refresh and enter new captcha on textbox
    Given Open login page
    When Click on button refresh
    And Input new captcha on textbox
    And Verify new captcha input is right
    And Verify the captcha is changed
    Then Close page
