package stepDefinitions.totalShift.orders;

import api.endpoints.totalShift.orders.CreateOrderApi;
import assertions.Assertions;
import dataBuilders.totalShift.orders.CreateOrderRequestBuilder;
import dto.totalShift.orders.CreateOrderDto;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import sharedContext.SharedStepContext;
import utils.faker.FakerUtil;

public class CreateOrderSteps {

    private final CreateOrderApi createOrderApi;
    private final SharedStepContext sharedStepContext;
    private Response response;
    private String xmlBody;
    private CreateOrderDto createOrderDto;

    public CreateOrderSteps(SharedStepContext sharedStepContext) {
        this.createOrderApi = new CreateOrderApi();
        this.sharedStepContext = sharedStepContext;
    }

    /**
     * Builds a create order request for the most recently created user and product.
     */
    @Given("I have valid order details for the created user and product")
    public void iHaveValidOrderDetailsForTheCreatedUserAndProduct() {
        String userId = this.sharedStepContext.getCreatedUserId();
        String productId = this.sharedStepContext.getCreatedProductId();
        String quantity = FakerUtil.getRandomQuantity();
        String status = FakerUtil.getRandomStatus();
        String notes = FakerUtil.getRandomNotes();

        this.createOrderDto = new CreateOrderDto(userId, productId, quantity, status, notes);
        this.sharedStepContext.setCreateOrderDto(createOrderDto);

        this.xmlBody = new CreateOrderRequestBuilder()
                .withUserId(userId)
                .withProductId(productId)
                .withQuantity(quantity)
                .withStatus(status)
                .withNotes(notes)
                .buildXml();
    }

    /**
     * Sends the create order request and stores the response.
     */
    @When("I send a create order request")
    public void iSendACreateOrderRequest() {
        this.response = this.createOrderApi.createOrder(xmlBody);
        this.sharedStepContext.setResponse(this.response);
    }

    /**
     * Asserts that the response echoes back the submitted order details.
     */
    @And("the response should contain the submitted order details")
    public void theResponseShouldContainTheSubmittedOrderDetails() {
        new Assertions(this.response.asString(), "order")
                .assertFieldEquals("user_id", this.createOrderDto.userId())
                .assertFieldEquals("product_id", this.createOrderDto.productId())
                .assertFieldEquals("quantity", this.createOrderDto.quantity())
                .assertFieldEquals("status", this.createOrderDto.status())
                .assertFieldEquals("notes", this.createOrderDto.notes());
    }

    /**
     * Asserts that the response contains a generated order ID.
     */
    @And("the response should contain a generated order ID")
    public void theResponseShouldContainAGeneratedOrderID() {
        new Assertions(this.response.asString(), "order")
                .assertFieldNotNull("id");
    }
}
