@order
@regression
Feature: Get Order

  This feature validates that a specific order can be retrieved by their ID through the Order API

  @sanity
  Scenario: Successfully retrieve an order by valid ID
    Given I have valid user details
    When I send a create user request
    Then the response status code should be 200
    And the response should contain the submitted user details
    And the response should contain a generated user ID

    Given I have valid product details
    When I send a create product request
    Then the response status code should be 200
    And the response should contain the submitted product details
    And the response should contain a generated product ID

    Given I have valid order details for the created user and product
    When I send a create order request
    Then the response status code should be 200
    And the response should contain the submitted order details
    And the response should contain a generated order ID

    And I have a valid get order by ID request body with the created order ID
    When I send a get order request
    Then the response status code should be 200
    And the response should contain the submitted order details

  Scenario: Fail to retrieve an order with incorrect ID format
    Given I have a get order by ID request with an invalid random ID
    When I send a get order request
    Then the response status code should be 400
    And the response should contain an order validation error message
