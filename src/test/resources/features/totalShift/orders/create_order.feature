@order
@regression
Feature: Create Order

  This feature validates that orders can be created successfully through the Order API
  using valid request data

  @sanity
  Scenario: Successfully create a new order with valid details
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
