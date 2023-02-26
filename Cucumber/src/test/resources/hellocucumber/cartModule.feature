Feature: A set of scenarios for testing the cart module

  Scenario: A customer adds a product to the cart
    Given the customer is logged in
    And the customer is in the home page
    And the customer navigates to the page of the product he wants to buy
    When the customer click on 'ADD TO CART' button
    Then message displayed 'Product successfully added to your shopping cart'
    And the product should be added to the cart

  Scenario: A seller changes the price of a product
    Given the seller is logged in
    And the seller is on the dashboard page
    And the seller navigates to the product editing page of the product he wants to change its price
    When the seller changes the price of the product
    And click on 'Save' button
    Then the price of the product should be changed

  Scenario: A customer adds a product to the cart and the seller changes the price of a product
    Given the customer is logged in
    And the customer is in the home page
    When the seller is logged in
    And the seller is on the dashboard page
    And the seller navigates to the product editing page of the product he wants to change its price
    And the customer navigates to the page of the product he wants to buy
    And the customer click on 'ADD TO CART' button
    And the seller changes the price of the product
    And click on 'Save' button
    Then message displayed 'Product successfully added to your shopping cart'
    And the product should be added to the cart
    And the price of the product should be changed
