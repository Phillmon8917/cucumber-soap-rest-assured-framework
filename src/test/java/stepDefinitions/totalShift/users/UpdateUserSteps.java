package stepDefinitions.totalShift.users;

import api.endpoints.totalShift.users.UpdateUserApi;
import assertions.Assertions;
import dataBuilders.totalShift.users.UpdateUserRequestBuilder;
import dto.totalShift.users.CreateUserDto;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.path.xml.XmlPath;
import sharedContext.SharedStepContext;
import utils.faker.FakerUtil;

public class UpdateUserSteps {

    private final SharedStepContext sharedStepContext;
    private final UpdateUserApi updateUserApi;
    private String xmlUpdateBody;
    private String newRole;
    private String generatedRandomGuid;

    public UpdateUserSteps(SharedStepContext sharedStepContext, UpdateUserApi updateUserApi) {
        this.sharedStepContext = sharedStepContext;
        this.updateUserApi = updateUserApi;
    }

    /**
     * Builds an update request that changes the created user's role, keeping other fields the same.
     */
    @And("I have valid update user details with role")
    public void iHaveValidUpdateUserDetailsWithRole() {
        XmlPath xmlPath = new XmlPath(this.sharedStepContext.getResponse().asString());
        CreateUserDto userToUpdate = this.sharedStepContext.getCreateUserDto();
        this.newRole = FakerUtil.getDifferentRole(userToUpdate.role());

        this.xmlUpdateBody = new UpdateUserRequestBuilder()
                .withId(xmlPath.getString("**.find { it.name() == 'user' }.id"))
                .withName(userToUpdate.name())
                .withEmail(userToUpdate.email())
                .withRole(newRole)
                .withAge(userToUpdate.age())
                .buildXml();
    }

    /**
     * Sends the update user request and stores the response.
     */
    @When("I send an update user request")
    public void iSendAnUpdateUserRequest() {
        this.sharedStepContext.setResponse(this.updateUserApi.updateUser(xmlUpdateBody));
    }

    /**
     * Asserts that the response reflects the new role.
     */
    @And("the response should contain the updated user role")
    public void theResponseShouldContainTheUpdatedUserName() {
        new Assertions(this.sharedStepContext.getResponse().asString(), "user")
                .assertFieldEquals("role", this.newRole);
    }

    /**
     * Builds an update request body targeting a random, non-existent user ID.
     */
    @Given("I have update user details with an invalid random ID")
    public void iHaveUpdateUserDetailsWithAnInvalidRandomID() {
        this.generatedRandomGuid = FakerUtil.getRandomUUID();
        this.xmlUpdateBody = new UpdateUserRequestBuilder()
                .withId(generatedRandomGuid)
                .buildXml();
    }

    /**
     * Asserts that the response contains a "user not found" fault message.
     */
    @And("the response should contain a user not found error message")
    public void theResponseShouldContainAUserNotFoundErrorMessage() {
        new Assertions(this.sharedStepContext.getResponse().asString(), "Fault")
                .assertFieldContains("faultstring", "User " + generatedRandomGuid + " not found");
    }

}
