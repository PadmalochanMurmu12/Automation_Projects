Feature: Dynamic Single Item Order Flow
  As an adventurous customer
  I want to add a random item from the menu to my cart
  So that I can test dynamic inventory processing

  Scenario: Add a random single product from the menu and place order
    Given I am on the FoodKart home page
    When I add a random item from the menu to my cart
    And I proceed to the checkout
    And I submit my payment details
    Then my order should be placed successfully