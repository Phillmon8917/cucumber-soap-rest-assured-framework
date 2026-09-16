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

    @And("I have a valid get user by ID request body with the created user ID")
    public void iHaveAValidGetUserByIDRequestBodyWithTheCreatedUserID() {

        XmlPath xmlPath = new XmlPath(sharedStepContext.getResponse().asString());
        this.xmlBody = new GetUserRequestBuilder()
                .withId(xmlPath.getString("**.find { it.name() == 'user' }.id"))
                .buildXml();
    }

    @When("I send a get user request")
    public void iSendAGetUserRequest() {
        this.sharedStepContext.setResponse(this.getUserApi.getUser(this.xmlBody));
    }

    @Given("I have a get user by ID request with an invalid random ID")
    public void iHaveAGetUserByIDRequestWithAnInvalidRandomID() {
        this.generatedRandomGuid = FakerUtil.getRandomUUID();
        this.xmlBody = new GetUserRequestBuilder()
                .withId(generatedRandomGuid)
                .buildXml();
    }

    @And("the response should contain a validation error message")
    public void theResponseShouldContainAValidationErrorMessage() {
        new Assertions(this.sharedStepContext.getResponse().asString(), "Fault")
                .assertFieldContains("faultstring", "User " + generatedRandomGuid + " not found");
    }
}
