package stepDefinitions.totalShift.users;

import api.endpoints.totalShift.users.CreateUserApi;
import assertions.Assertions;
import dataBuilders.totalShift.users.CreateUserRequestBuilder;
import dto.totalShift.users.CreateUserDto;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import sharedContext.SharedStepContext;
import utils.date.DateUtil;
import utils.faker.FakerUtil;

public class CreateUserSteps {

    private final CreateUserApi createUserApi;
    private Response response;
    private final SharedStepContext sharedStepContext;
    private String xmlBody;
    private CreateUserDto createUserDto;

    public CreateUserSteps(SharedStepContext sharedStepContext){
        this.createUserApi = new CreateUserApi();
        this.sharedStepContext = sharedStepContext;
    }

    /**
     * Generates a valid, random set of user details and builds the create request body.
     */
    @Given("I have valid user details")
    public void iHaveValidUserDetails() {
        String name = FakerUtil.getRandomName();
        String email = FakerUtil.getRandomEmail();
        String role = FakerUtil.getRandomRole();
        String age = FakerUtil.getRandomAge();

        this.createUserDto = new CreateUserDto(name, email, role, age);
        this.sharedStepContext.setCreateUserDto(createUserDto);

        this.xmlBody = new CreateUserRequestBuilder()
                .withName(name)
                .withEmail(email)
                .withRole(role)
                .withAge(age)
                .buildXml();
    }

    /**
     * Sends the create user request and stores the response and generated user ID.
     */
    @When("I send a create user request")
    public void iSendACreateUserRequest() {
        this.response = this.createUserApi.CreateANewUser(xmlBody);
        this.sharedStepContext.setResponse(this.response);

        XmlPath xmlPath = new XmlPath(this.response.asString());
        this.sharedStepContext.setCreatedUserId(xmlPath.getString("**.find { it.name() == 'user' }.id"));
    }

    /**
     * Asserts that the response echoes back the submitted user details.
     */
    @And("the response should contain the submitted user details")
    public void theResponseShouldContainTheSubmittedUserDetails() {
        new Assertions(this.response.asString(), "user")
                .assertFieldEquals("name", this.createUserDto.name())
                .assertFieldEquals("email", this.createUserDto.email())
                .assertFieldEquals("role", this.createUserDto.role())
                .assertFieldEquals("age", this.createUserDto.age());
    }

    /**
     * Asserts that the response contains a generated user ID.
     */
    @And("the response should contain a generated user ID")
    public void theResponseShouldContainAGeneratedUserID() {
        new Assertions(this.response.asString(), "user")
                .assertFieldNotNull("id");
    }

    /**
     * Asserts that the response's creation date matches today's date.
     */
    @And("the response should contain today's creation date")
    public void theResponseShouldContainTodaySCreationDate() {
        new Assertions(this.response.asString(), "user")
                .assertFieldContains("created_at", DateUtil.getDateAsString(0));
    }

    /**
     * Builds a create request body that omits the email address.
     */
    @Given("I have user details without an email address")
    public void iHaveUserDetailsWithoutAnEmailAddress() {
        this.xmlBody = new CreateUserRequestBuilder()
                .withName(FakerUtil.getRandomName())
                .withRole(FakerUtil.getRandomRole())
                .withAge(FakerUtil.getRandomAge())
                .buildXml();
    }

    /**
     * Asserts that the response contains an email-required validation error.
     */
    @And("the response should contain an email validation error")
    public void theResponseShouldContainAnEmailValidationError() {
        new Assertions(this.response.asString(), "Fault")
                .assertFieldContains("faultstring", "email are required");
    }

    /**
     * Asserts that the previously created user has an email address on record.
     */
    @Given("a user already exists with the same email address")
    public void aUserAlreadyExistsWithTheSameEmailAddress() {
        new Assertions(this.response.asString(), "user")
                .assertFieldNotNull("email");
    }

    /**
     * Builds a create request body reusing the previously created user's email address.
     */
    @And("I have user details with that email address")
    public void iHaveUserDetailsWithThatEmailAddress() {
        this.xmlBody = new CreateUserRequestBuilder()
                .withName(FakerUtil.getRandomName())
                .withEmail(this.createUserDto.email())
                .withRole(FakerUtil.getRandomRole())
                .withAge(FakerUtil.getRandomAge())
                .buildXml();
    }

    /**
     * Asserts that the response contains a duplicate-email error message.
     */
    @And("the response should contain a duplicate user error message")
    public void theResponseShouldContainADuplicateUserErrorMessage() {
        new Assertions(this.response.asString(), "Fault")
                .assertFieldContains("faultstring", "email already exists");
    }

    /**
     * Builds a create request body with a malformed email address.
     */
    @Given("I have user details with an invalid email address")
    public void iHaveUserDetailsWithAnInvalidEmailAddress() {
        this.xmlBody = new CreateUserRequestBuilder()
                .withName(FakerUtil.getRandomName())
                .withEmail(FakerUtil.getRandomName())
                .withRole(FakerUtil.getRandomRole())
                .withAge(FakerUtil.getRandomAge())
                .buildXml();
    }

    /**
     * Asserts that the response contains an invalid-email validation error.
     */
    @And("the response should contain an email validation error message")
    public void theResponseShouldContainAnEmailValidationErrorMessage() {
        new Assertions(this.response.asString(), "Fault")
                .assertFieldContains("faultstring", "invalid email");
    }
}
