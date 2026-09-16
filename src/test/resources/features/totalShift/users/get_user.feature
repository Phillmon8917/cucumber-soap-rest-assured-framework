@user
@regression
Feature: Get User

  This feature validates that a specific user can be retrieved by their ID through the User API

  @sanity
  Scenario: Successfully retrieve a user by valid ID
    Given I have valid user details
    When I send a create user request
    Then the response status code should be 200
    And the response should contain the submitted user details
    And the response should contain a generated user ID

    And I have a valid get user by ID request body with the created user ID
    When I send a get user request
    Then the response status code should be 200
    And the response should contain the submitted user details

  Scenario: Fail to retrieve a user with incorrect ID format
    Given I have a get user by ID request with an invalid random ID
    When I send a get user request
    Then the response status code should be 400
    And the response should contain a validation error message