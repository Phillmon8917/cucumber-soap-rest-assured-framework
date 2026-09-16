@user
@regression
Feature: Update User

  This feature validates that users can be updated successfully through the User API

  @sanity
  Scenario: Successfully update a user with valid details
    Given I have valid user details
    When I send a create user request
    Then the response status code should be 200
    And the response should contain the submitted user details
    And the response should contain a generated user ID

    And I have valid update user details with role
    When I send an update user request
    Then the response status code should be 200
    And the response should contain the updated user role

  Scenario: Fail to update a user with an incorrect ID
    Given I have update user details with an invalid random ID
    When I send an update user request
    Then the response status code should be 400
    And the response should contain a user not found error message