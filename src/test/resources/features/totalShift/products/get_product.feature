@product
@regression
Feature: Get Product

  This feature validates that a specific product can be retrieved by their ID through the Product API

  @sanity
  Scenario: Successfully retrieve a product by valid ID
    Given I have valid product details
    When I send a create product request
    Then the response status code should be 200
    And the response should contain the submitted product details
    And the response should contain a generated product ID

    And I have a valid get product by ID request body with the created product ID
    When I send a get product request
    Then the response status code should be 200
    And the response should contain the submitted product details

  Scenario: Fail to retrieve a product with incorrect ID format
    Given I have a get product by ID request with an invalid random ID
    When I send a get product request
    Then the response status code should be 400
    And the response should contain a product validation error message
