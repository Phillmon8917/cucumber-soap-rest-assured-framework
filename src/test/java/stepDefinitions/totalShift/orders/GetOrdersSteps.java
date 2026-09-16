package stepDefinitions.totalShift.orders;

import api.endpoints.totalShift.orders.GetOrdersApi;
import assertions.Assertions;
import dataBuilders.totalShift.orders.GetOrdersRequestBuilder;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import sharedContext.SharedStepContext;

public class GetOrdersSteps {

    private final GetOrdersApi getOrdersApi;
    private final SharedStepContext sharedStepContext;
    private Response response;
    private String xmlBody;

    public GetOrdersSteps(SharedStepContext sharedStepContext) {
        this.sharedStepContext = sharedStepContext;
        this.getOrdersApi = new GetOrdersApi();
    }

    /**
     * Builds a get orders request body with a fixed page and limit.
     */
    @Given("I have a valid get orders request body")
    public void iHaveAValidGetOrdersRequestBody() {
        this.xmlBody = new GetOrdersRequestBuilder()
                .withPage("1")
                .withLimit("7")
                .buildXml();
    }

    /**
     * Sends the get orders request and stores the response.
     */
    @When("I send a get orders request")
    public void iSendAGetOrdersRequest() {
        this.response = this.getOrdersApi.getOrders(this.xmlBody);
        this.sharedStepContext.setResponse(this.response);
    }

    /**
     * Asserts that the response contains at least one order.
     */
    @And("the response should contain a list of orders")
    public void theResponseShouldContainAListOfOrders() {
        new Assertions(this.response.asString(), "order")
                .assertFieldNotNull("");
    }
}
