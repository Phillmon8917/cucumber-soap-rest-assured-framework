@user
@regression
Feature: Create User

  This feature validates that users can be created successfully through the User API
  using valid request data

  @sanity
  Scenario: Successfully create a new user with valid details
    Given I have valid user details
    When I send a create user request
    Then the response status code should be 200
    And the response should contain the submitted user details
    And the response should contain a generated user ID
    And the response should contain today's creation date

  #Scenario: Fail to create a user with missing email address
    #Given I have user details without an email address
    #When I send a create user request
    #Then the response status code should be 400
    #And the response should contain an email validation error

  #Scenario: Fail to create a user with a duplicate email address
    #Given I have valid user details
    #When I send a create user request
    #Then the response status code should be 200
    #And the response should contain the submitted user details
    #And the response should contain a generated user ID

    #Given a user already exists with the same email address
    #And I have user details with that email address
    #When I send a create user request
    #Then the response status code should be 409
    #And the response should contain a duplicate user error message

  #Scenario: Fail to create a user with an invalid email address
    #Given I have user details with an invalid email address
    #When I send a create user request
    #Then the response status code should be 400
    #And the response should contain an email validation error message
