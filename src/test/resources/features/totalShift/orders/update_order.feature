@order
@regression
Feature: Update Order

  This feature validates that orders can be updated successfully through the Order API

  @sanity
  Scenario: Successfully update an order with valid details
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

    And I have valid update order details with status
    When I send an update order request
    Then the response status code should be 200
    And the response should contain the updated order status

  Scenario: Fail to update an order with an incorrect ID
    Given I have update order details with an invalid random ID
    When I send an update order request
    Then the response status code should be 400
    And the response should contain an order not found error message
