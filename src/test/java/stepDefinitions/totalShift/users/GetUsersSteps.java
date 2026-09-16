package stepDefinitions.totalShift.users;

import api.endpoints.totalShift.users.GetUsersApi;
import assertions.Assertions;
import dataBuilders.totalShift.users.GetUsersRequestBuilder;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import sharedContext.SharedStepContext;

public class GetUsersSteps {

    private final GetUsersApi getUsersApi;
    private final SharedStepContext sharedStepContext;
    private Response response;
    private String xmlBody;

    public GetUsersSteps(SharedStepContext sharedStepContext) {
        this.sharedStepContext = sharedStepContext;
        this.getUsersApi = new GetUsersApi();
    }

    /**
     * Builds a get users request body with a fixed page and limit.
     */
    @Given("I have a valid get users request body")
    public void iHaveAValidGetUsersRequestBody() {
        this.xmlBody = new GetUsersRequestBuilder()
                .withPage("1") //Hardcoded because the api doesnt have users by default unless you add first
                .withLimit("7") //Otherwise use something like FakerUtil.getRandomNumber(1, 5))
                .buildXml();
    }

    /**
     * Sends the get users request and stores the response.
     */
    @When("I send a get users request")
    public void iSendAGetUsersRequest() {
        this.response = this.getUsersApi.getUsers(this.xmlBody);
        this.sharedStepContext.setResponse(this.response);
    }

    /**
     * Asserts that the response contains at least one user.
     */
    @And("the response should contain a list of users")
    public void theResponseShouldContainAListOfUsers() {
        new Assertions(this.response.asString(), "user")
                .assertFieldNotNull("");
    }
}
