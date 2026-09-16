package api.endpoints.totalShift.products;

import api.base.BaseApi;
import api.helpers.Headers;
import io.restassured.http.Method;
import io.restassured.response.Response;
import utils.env.EnvLoader;

public class UpdateProductApi extends BaseApi {

    public UpdateProductApi() {
        super(EnvLoader.getEnvValue("BASE_URI"));
        super.setBasePath("/soap");
        super.logAllRequestData();
        super.logAllResponseData();
    }

    public Response updateProduct(String xmlBody) {
        super.setHeaders(Headers.buildHeaders("UpdateProduct"));
        super.setRequestBody(xmlBody);
        return super.sendRequest(Method.POST);
    }
}
