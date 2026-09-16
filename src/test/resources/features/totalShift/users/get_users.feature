@user
@regression
Feature: Get Users

  This feature validates that users can be retrieved successfully through the User API

  @sanity
  Scenario: Successfully retrieve users
    Given I have valid user details
    When I send a create user request
    Then the response status code should be 200
    And the response should contain the submitted user details
    And the response should contain a generated user ID
    And the response should contain today's creation date

    Given I have a valid get users request body
    When I send a get users request
    Then the response status code should be 200
    And the response should contain a list of users

