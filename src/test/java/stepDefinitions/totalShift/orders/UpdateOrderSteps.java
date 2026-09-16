package stepDefinitions.totalShift.orders;

import api.endpoints.totalShift.orders.UpdateOrderApi;
import assertions.Assertions;
import dataBuilders.totalShift.orders.UpdateOrderRequestBuilder;
import dto.totalShift.orders.CreateOrderDto;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.path.xml.XmlPath;
import sharedContext.SharedStepContext;
import utils.faker.FakerUtil;

public class UpdateOrderSteps {

    private final SharedStepContext sharedStepContext;
    private final UpdateOrderApi updateOrderApi;
    private String xmlUpdateBody;
    private String newStatus;
    private String generatedRandomGuid;

    public UpdateOrderSteps(SharedStepContext sharedStepContext, UpdateOrderApi updateOrderApi) {
        this.sharedStepContext = sharedStepContext;
        this.updateOrderApi = updateOrderApi;
    }

    /**
     * Builds an update request that changes the created order's status, keeping other fields the same.
     */
    @And("I have valid update order details with status")
    public void iHaveValidUpdateOrderDetailsWithStatus() {
        XmlPath xmlPath = new XmlPath(this.sharedStepContext.getResponse().asString());
        CreateOrderDto orderToUpdate = this.sharedStepContext.getCreateOrderDto();
        this.newStatus = FakerUtil.getDifferentStatus(orderToUpdate.status());

        this.xmlUpdateBody = new UpdateOrderRequestBuilder()
                .withId(xmlPath.getString("**.find { it.name() == 'order' }.id"))
                .withStatus(this.newStatus)
                .withQuantity(orderToUpdate.quantity())
                .withNotes(orderToUpdate.notes())
                .buildXml();
    }

    /**
     * Sends the update order request and stores the response.
     */
    @When("I send an update order request")
    public void iSendAnUpdateOrderRequest() {
        this.sharedStepContext.setResponse(this.updateOrderApi.updateOrder(xmlUpdateBody));
    }

    /**
     * Asserts that the response reflects the new status.
     */
    @And("the response should contain the updated order status")
    public void theResponseShouldContainTheUpdatedOrderStatus() {
        new Assertions(this.sharedStepContext.getResponse().asString(), "order")
                .assertFieldEquals("status", this.newStatus);
    }

    /**
     * Builds an update request body targeting a random, non-existent order ID.
     */
    @Given("I have update order details with an invalid random ID")
    public void iHaveUpdateOrderDetailsWithAnInvalidRandomID() {
        this.generatedRandomGuid = FakerUtil.getRandomUUID();
        this.xmlUpdateBody = new UpdateOrderRequestBuilder()
                .withId(generatedRandomGuid)
                .buildXml();
    }

    /**
     * Asserts that the response contains an "order not found" fault message.
     */
    @And("the response should contain an order not found error message")
    public void theResponseShouldContainAnOrderNotFoundErrorMessage() {
        new Assertions(this.sharedStepContext.getResponse().asString(), "Fault")
                .assertFieldContains("faultstring", "Order " + generatedRandomGuid + " not found");
    }
}
