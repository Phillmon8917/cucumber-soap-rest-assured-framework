@product
@regression
Feature: Create Product

  This feature validates that products can be created successfully through the Product API
  using valid request data

  @sanity
  Scenario: Successfully create a new product with valid details
    Given I have valid product details
    When I send a create product request
    Then the response status code should be 200
    And the response should contain the submitted product details
    And the response should contain a generated product ID
