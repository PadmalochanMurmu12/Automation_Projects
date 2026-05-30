Feature: Dynamic Single Item Multi-Quantity Order Flow

  Scenario: Add a random item, dynamically increase quantity, and place order
    Given I am on the FoodKart home page
    When I add a random item from the menu to my cart
    And I increase the quantity of the dynamically added item to 4
    Then the cart should accurately reflect the dynamic total price
    And I proceed to the checkout
    And I submit my payment details
    Then my order should be placed successfully