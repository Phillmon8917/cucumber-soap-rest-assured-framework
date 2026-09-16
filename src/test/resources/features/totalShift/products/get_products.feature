@product
@regression
Feature: Get Products

  This feature validates that products can be retrieved successfully through the Product API

  @sanity
  Scenario: Successfully retrieve products
    Given I have valid product details
    When I send a create product request
    Then the response status code should be 200
    And the response should contain the submitted product details
    And the response should contain a generated product ID

    Given I have a valid get products request body
    When I send a get products request
    Then the response status code should be 200
    And the response should contain a list of products
