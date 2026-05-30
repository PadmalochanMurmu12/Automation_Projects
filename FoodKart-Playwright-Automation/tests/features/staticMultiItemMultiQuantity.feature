Feature: Static Multi-Item Multi-Quantity Order Flow

  Scenario: Add multiple static items from JSON, increase quantities, and place order
    Given I am on the FoodKart home page
    When I configure my cart with the static multi-quantity items from JSON
    And I proceed to the checkout
    And I submit my payment details
    Then my order should be placed successfully