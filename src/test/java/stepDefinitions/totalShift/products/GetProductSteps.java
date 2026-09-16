package stepDefinitions.totalShift.products;

import api.endpoints.totalShift.products.GetProductApi;
import assertions.Assertions;
import dataBuilders.totalShift.products.GetProductRequestBuilder;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.path.xml.XmlPath;
import sharedContext.SharedStepContext;
import utils.faker.FakerUtil;

public class GetProductSteps {

    private final SharedStepContext sharedStepContext;
    private final GetProductApi getProductApi;
    private String xmlBody;
    private String generatedRandomGuid;

    public GetProductSteps(SharedStepContext sharedStepContext) {
        this.getProductApi = new GetProductApi();
        this.sharedStepContext = sharedStepContext;
    }

    /**
     * Builds a get product request body using the ID of the most recently created product.
     */
    @And("I have a valid get product by ID request body with the created product ID")
    public void iHaveAValidGetProductByIDRequestBodyWithTheCreatedProductID() {
        XmlPath xmlPath = new XmlPath(sharedStepContext.getResponse().asString());
        this.xmlBody = new GetProductRequestBuilder()
                .withId(xmlPath.getString("**.find { it.name() == 'product' }.id"))
                .buildXml();
    }

    /**
     * Sends the get product request and stores the response.
     */
    @When("I send a get product request")
    public void iSendAGetProductRequest() {
        this.sharedStepContext.setResponse(this.getProductApi.getProduct(this.xmlBody));
    }

    /**
     * Builds a get product request body using a random, non-existent ID.
     */
    @Given("I have a get product by ID request with an invalid random ID")
    public void iHaveAGetProductByIDRequestWithAnInvalidRandomID() {
        this.generatedRandomGuid = FakerUtil.getRandomUUID();
        this.xmlBody = new GetProductRequestBuilder()
                .withId(generatedRandomGuid)
                .buildXml();
    }

    /**
     * Asserts that the response contains a "product not found" fault message.
     */
    @And("the response should contain a product validation error message")
    public void theResponseShouldContainAProductValidationErrorMessage() {
        new Assertions(this.sharedStepContext.getResponse().asString(), "Fault")
                .assertFieldContains("faultstring", "Product " + generatedRandomGuid + " not found");
    }
}
