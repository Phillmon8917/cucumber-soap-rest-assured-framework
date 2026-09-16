package stepDefinitions.totalShift.products;

import api.endpoints.totalShift.products.GetProductsApi;
import assertions.Assertions;
import dataBuilders.totalShift.products.GetProductsRequestBuilder;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import sharedContext.SharedStepContext;

public class GetProductsSteps {

    private final GetProductsApi getProductsApi;
    private final SharedStepContext sharedStepContext;
    private Response response;
    private String xmlBody;

    public GetProductsSteps(SharedStepContext sharedStepContext) {
        this.sharedStepContext = sharedStepContext;
        this.getProductsApi = new GetProductsApi();
    }

    /**
     * Builds a get products request body with a fixed page and limit.
     */
    @Given("I have a valid get products request body")
    public void iHaveAValidGetProductsRequestBody() {
        this.xmlBody = new GetProductsRequestBuilder()
                .withPage("1")
                .withLimit("7")
                .buildXml();
    }

    /**
     * Sends the get products request and stores the response.
     */
    @When("I send a get products request")
    public void iSendAGetProductsRequest() {
        this.response = this.getProductsApi.getProducts(this.xmlBody);
        this.sharedStepContext.setResponse(this.response);
    }

    /**
     * Asserts that the response contains at least one product.
     */
    @And("the response should contain a list of products")
    public void theResponseShouldContainAListOfProducts() {
        new Assertions(this.response.asString(), "product")
                .assertFieldNotNull("");
    }
}
