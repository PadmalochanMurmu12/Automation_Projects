Feature: Multi-Item Order Flow
  As a very hungry customer
  I want to select multiple items from the menu and pay for them
  So that I can feast with my friends

  Scenario: Add multiple products from configuration and place order
    Given I am on the FoodKart home page
    When I add all products from the multiple items configuration to my cart
    And I proceed to the checkout
    And I submit my payment details
    Then my order should be placed successfully