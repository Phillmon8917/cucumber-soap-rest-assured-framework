package stepDefinitions.totalShift.products;

import api.endpoints.totalShift.products.UpdateProductApi;
import assertions.Assertions;
import dataBuilders.totalShift.products.UpdateProductRequestBuilder;
import dto.totalShift.products.CreateProductDto;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.path.xml.XmlPath;
import sharedContext.SharedStepContext;
import utils.faker.FakerUtil;

public class UpdateProductSteps {

    private final SharedStepContext sharedStepContext;
    private final UpdateProductApi updateProductApi;
    private String xmlUpdateBody;
    private String newPrice;
    private String generatedRandomGuid;

    public UpdateProductSteps(SharedStepContext sharedStepContext, UpdateProductApi updateProductApi) {
        this.sharedStepContext = sharedStepContext;
        this.updateProductApi = updateProductApi;
    }

    @And("I have valid update product details with price")
    public void iHaveValidUpdateProductDetailsWithPrice() {
        XmlPath xmlPath = new XmlPath(this.sharedStepContext.getResponse().asString());
        CreateProductDto productToUpdate = this.sharedStepContext.getCreateProductDto();
        this.newPrice = FakerUtil.getRandomPrice();

        this.xmlUpdateBody = new UpdateProductRequestBuilder()
                .withId(xmlPath.getString("**.find { it.name() == 'product' }.id"))
                .withName(productToUpdate.name())
                .withPrice(this.newPrice)
                .withDescription(productToUpdate.description())
                .withStock(productToUpdate.stock())
                .withCategory(productToUpdate.category())
                .buildXml();
    }

    @When("I send an update product request")
    public void iSendAnUpdateProductRequest() {
        this.sharedStepContext.setResponse(this.updateProductApi.updateProduct(xmlUpdateBody));
    }

    @And("the response should contain the updated product price")
    public void theResponseShouldContainTheUpdatedProductPrice() {
        new Assertions(this.sharedStepContext.getResponse().asString(), "product")
                .assertFieldEquals("price", this.newPrice);
    }

    @Given("I have update product details with an invalid random ID")
    public void iHaveUpdateProductDetailsWithAnInvalidRandomID() {
        this.generatedRandomGuid = FakerUtil.getRandomUUID();
        this.xmlUpdateBody = new UpdateProductRequestBuilder()
                .withId(generatedRandomGuid)
                .buildXml();
    }

    @And("the response should contain a product not found error message")
    public void theResponseShouldContainAProductNotFoundErrorMessage() {
        new Assertions(this.sharedStepContext.getResponse().asString(), "Fault")
                .assertFieldContains("faultstring", "Product " + generatedRandomGuid + " not found");
    }
}
