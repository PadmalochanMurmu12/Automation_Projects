Feature: Single Item Order Flow
  As a hungry customer
  I want to select an item from the menu and pay for it
  So that I can get my food delivered

  Scenario: Add a specific single item from JSON and place order
    Given I am on the FoodKart home page
    When I add the "Margherita Pizza" to my cart
    And I proceed to the checkout
    And I submit my payment details
    Then my order should be placed successfully