package api.endpoints.totalShift.orders;

import api.base.BaseApi;
import api.helpers.Headers;
import io.restassured.http.Method;
import io.restassured.response.Response;
import utils.env.EnvLoader;

public class CreateOrderApi extends BaseApi {

    public CreateOrderApi() {
        super(EnvLoader.getEnvValue("BASE_URI"));
        super.setBasePath("/soap");
        super.logAllRequestData();
        super.logAllResponseData();
    }

    public Response createOrder(String xmlBody) {
        super.setHeaders(Headers.buildHeaders("CreateOrder"));
        super.setRequestBody(xmlBody);
        return super.sendRequest(Method.POST);
    }
}
