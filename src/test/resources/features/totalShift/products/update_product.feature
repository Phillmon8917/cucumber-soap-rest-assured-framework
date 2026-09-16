@product
@regression
Feature: Update Product

  This feature validates that products can be updated successfully through the Product API

  @sanity
  Scenario: Successfully update a product with valid details
    Given I have valid product details
    When I send a create product request
    Then the response status code should be 200
    And the response should contain the submitted product details
    And the response should contain a generated product ID

    And I have valid update product details with price
    When I send an update product request
    Then the response status code should be 200
    And the response should contain the updated product price

  Scenario: Fail to update a product with an incorrect ID
    Given I have update product details with an invalid random ID
    When I send an update product request
    Then the response status code should be 400
    And the response should contain a product not found error message
