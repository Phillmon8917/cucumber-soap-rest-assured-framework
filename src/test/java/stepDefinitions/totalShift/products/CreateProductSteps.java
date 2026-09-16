package stepDefinitions.totalShift.products;

import api.endpoints.totalShift.products.CreateProductApi;
import assertions.Assertions;
import dataBuilders.totalShift.products.CreateProductRequestBuilder;
import dto.totalShift.products.CreateProductDto;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import sharedContext.SharedStepContext;
import utils.faker.FakerUtil;

public class CreateProductSteps {

    private final CreateProductApi createProductApi;
    private final SharedStepContext sharedStepContext;
    private Response response;
    private String xmlBody;
    private CreateProductDto createProductDto;

    public CreateProductSteps(SharedStepContext sharedStepContext) {
        this.createProductApi = new CreateProductApi();
        this.sharedStepContext = sharedStepContext;
    }

    @Given("I have valid product details")
    public void iHaveValidProductDetails() {
        String name = FakerUtil.getRandomProductName();
        String price = FakerUtil.getRandomPrice();
        String description = FakerUtil.getRandomDescription();
        String stock = FakerUtil.getRandomStock();
        String category = FakerUtil.getRandomCategory();

        this.createProductDto = new CreateProductDto(name, price, description, stock, category);
        this.sharedStepContext.setCreateProductDto(createProductDto);

        this.xmlBody = new CreateProductRequestBuilder()
                .withName(name)
                .withPrice(price)
                .withDescription(description)
                .withStock(stock)
                .withCategory(category)
                .buildXml();
    }

    @When("I send a create product request")
    public void iSendACreateProductRequest() {
        this.response = this.createProductApi.createProduct(xmlBody);
        this.sharedStepContext.setResponse(this.response);

        XmlPath xmlPath = new XmlPath(this.response.asString());
        this.sharedStepContext.setCreatedProductId(xmlPath.getString("**.find { it.name() == 'product' }.id"));
    }

    @And("the response should contain the submitted product details")
    public void theResponseShouldContainTheSubmittedProductDetails() {
        new Assertions(this.response.asString(), "product")
                .assertFieldEquals("name", this.createProductDto.name())
                .assertFieldEquals("price", this.createProductDto.price())
                .assertFieldEquals("description", this.createProductDto.description())
                .assertFieldEquals("stock", this.createProductDto.stock())
                .assertFieldEquals("category", this.createProductDto.category());
    }

    @And("the response should contain a generated product ID")
    public void theResponseShouldContainAGeneratedProductID() {
        new Assertions(this.response.asString(), "product")
                .assertFieldNotNull("id");
    }
}
