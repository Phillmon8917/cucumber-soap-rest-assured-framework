package stepDefinitions.totalShift.orders;

import api.endpoints.totalShift.orders.GetOrderApi;
import assertions.Assertions;
import dataBuilders.totalShift.orders.GetOrderRequestBuilder;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.path.xml.XmlPath;
import sharedContext.SharedStepContext;
import utils.faker.FakerUtil;

public class GetOrderSteps {

    private final SharedStepContext sharedStepContext;
    private final GetOrderApi getOrderApi;
    private String xmlBody;
    private String generatedRandomGuid;

    public GetOrderSteps(SharedStepContext sharedStepContext) {
        this.getOrderApi = new GetOrderApi();
        this.sharedStepContext = sharedStepContext;
    }

    /**
     * Builds a get order request body using the ID of the most recently created order.
     */
    @And("I have a valid get order by ID request body with the created order ID")
    public void iHaveAValidGetOrderByIDRequestBodyWithTheCreatedOrderID() {
        XmlPath xmlPath = new XmlPath(sharedStepContext.getResponse().asString());
        this.xmlBody = new GetOrderRequestBuilder()
                .withId(xmlPath.getString("**.find { it.name() == 'order' }.id"))
                .buildXml();
    }

    /**
     * Sends the get order request and stores the response.
     */
    @When("I send a get order request")
    public void iSendAGetOrderRequest() {
        this.sharedStepContext.setResponse(this.getOrderApi.getOrder(this.xmlBody));
    }

    /**
     * Builds a get order request body using a random, non-existent ID.
     */
    @Given("I have a get order by ID request with an invalid random ID")
    public void iHaveAGetOrderByIDRequestWithAnInvalidRandomID() {
        this.generatedRandomGuid = FakerUtil.getRandomUUID();
        this.xmlBody = new GetOrderRequestBuilder()
                .withId(generatedRandomGuid)
                .buildXml();
    }

    /**
     * Asserts that the response contains an "order not found" fault message.
     */
    @And("the response should contain an order validation error message")
    public void theResponseShouldContainAnOrderValidationErrorMessage() {
        new Assertions(this.sharedStepContext.getResponse().asString(), "Fault")
                .assertFieldContains("faultstring", "Order " + generatedRandomGuid + " not found");
    }
}
