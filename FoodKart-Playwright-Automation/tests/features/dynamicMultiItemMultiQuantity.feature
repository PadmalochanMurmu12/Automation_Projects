Feature: Dynamic Multi-Item Multi-Quantity Order Flow

  Scenario: Add multiple random items, dynamically increase quantities, and place order
    Given I am on the FoodKart home page
    When I dynamically add 3 random items from the menu to my cart
    And I increase the quantity of all dynamically added items to 2
    Then the cart should accurately reflect the dynamic total prices for all items
    And I proceed to the checkout
    And I submit my payment details
    Then my order should be placed successfully