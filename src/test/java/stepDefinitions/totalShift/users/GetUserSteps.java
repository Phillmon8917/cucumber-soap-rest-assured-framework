package stepDefinitions.totalShift.users;

import api.endpoints.totalShift.users.GetUserApi;
import assertions.Assertions;
import dataBuilders.totalShift.users.GetUserRequestBuilder;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.path.xml.XmlPath;
import sharedContext.SharedStepContext;
import utils.faker.FakerUtil;

public class GetUserSteps {

    private final SharedStepContext sharedStepContext;
    private final GetUserApi getUserApi;
    private String xmlBody;
    private String generatedRandomGuid;

    public GetUserSteps (SharedStepContext sharedStepContext){
        this.getUserApi = new GetUserApi();
        this.sharedStepContext = sharedStepContext;
    }

    /**
     * Builds a get user request body using the ID of the most recently created user.
     */
    @And("I have a valid get user by ID request body with the created user ID")
    public void iHaveAValidGetUserByIDRequestBodyWithTheCreatedUserID() {

        XmlPath xmlPath = new XmlPath(sharedStepContext.getResponse().asString());
        this.xmlBody = new GetUserRequestBuilder()
                .withId(xmlPath.getString("**.find { it.name() == 'user' }.id"))
                .buildXml();
    }

    /**
     * Sends the get user request and stores the response.
     */
    @When("I send a get user request")
    public void iSendAGetUserRequest() {
        this.sharedStepContext.setResponse(this.getUserApi.getUser(this.xmlBody));
    }

    /**
     * Builds a get user request body using a random, non-existent ID.
     */
    @Given("I have a get user by ID request with an invalid random ID")
    public void iHaveAGetUserByIDRequestWithAnInvalidRandomID() {
        this.generatedRandomGuid = FakerUtil.getRandomUUID();
        this.xmlBody = new GetUserRequestBuilder()
                .withId(generatedRandomGuid)
                .buildXml();
    }

    /**
     * Asserts that the response contains a "user not found" fault message.
     */
    @And("the response should contain a validation error message")
    public void theResponseShouldContainAValidationErrorMessage() {
        new Assertions(this.sharedStepContext.getResponse().asString(), "Fault")
                .assertFieldContains("faultstring", "User " + generatedRandomGuid + " not found");
    }
}
