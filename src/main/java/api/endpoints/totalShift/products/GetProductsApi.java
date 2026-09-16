package api.endpoints.totalShift.products;

import api.base.BaseApi;
import api.helpers.Headers;
import io.restassured.http.Method;
import io.restassured.response.Response;
import utils.env.EnvLoader;

public class GetProductsApi extends BaseApi {

    public GetProductsApi() {
        super(EnvLoader.getEnvValue("BASE_URI"));
        super.setBasePath("/soap");
        super.logAllRequestData();
        super.logAllResponseData();
    }

    public Response getProducts(String xmlBody) {
        super.setHeaders(Headers.buildHeaders("GetProducts"));
        super.setRequestBody(xmlBody);
        return super.sendRequest(Method.POST);
    }
}
