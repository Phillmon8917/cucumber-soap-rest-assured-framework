package api.endpoints.totalShift.products;

import api.base.BaseApi;
import api.helpers.Headers;
import io.restassured.http.Method;
import io.restassured.response.Response;
import utils.env.EnvLoader;

public class CreateProductApi extends BaseApi {

    public CreateProductApi() {
        super(EnvLoader.getEnvValue("BASE_URI"));
        super.setBasePath("/soap");
        super.logAllRequestData();
        super.logAllResponseData();
    }

    /**
     * Sends a CreateProduct SOAP request with the given XML body.
     */
    public Response createProduct(String xmlBody) {
        super.setHeaders(Headers.buildHeaders("CreateProduct"));
        super.setRequestBody(xmlBody);
        return super.sendRequest(Method.POST);
    }
}
