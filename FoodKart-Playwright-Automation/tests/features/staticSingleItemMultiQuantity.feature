Feature: Static Item Order multiple quantity Flow

  Scenario: Add a specific single item, increase its quantity, and place the order
    Given I am on the FoodKart home page
    When I add the "Margherita Pizza" to my cart
    And I increase the quantity of "Margherita Pizza" to 3
    Then the cart should reflect the updated quantity and total price
    And I proceed to the checkout
    And I submit my payment details
    Then my order should be placed successfully