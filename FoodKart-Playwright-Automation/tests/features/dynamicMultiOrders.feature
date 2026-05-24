Feature: Dynamic Multi-Item Order Flow
  As an indecisive but hungry customer
  I want to add several random items from the menu to my cart
  So that I can test dynamic loop processing

  Scenario: Add multiple random products from the menu and place order
    Given I am on the FoodKart home page
    When I add 3 random items from the menu to my cart
    And I proceed to the checkout
    And I submit my payment details
    Then my order should be placed successfully