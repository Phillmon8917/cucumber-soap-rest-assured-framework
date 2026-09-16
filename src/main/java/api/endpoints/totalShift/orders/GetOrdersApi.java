package api.endpoints.totalShift.orders;

import api.base.BaseApi;
import api.helpers.Headers;
import io.restassured.http.Method;
import io.restassured.response.Response;
import utils.env.EnvLoader;

public class GetOrdersApi extends BaseApi {

    public GetOrdersApi() {
        super(EnvLoader.getEnvValue("BASE_URI"));
        super.setBasePath("/soap");
        super.logAllRequestData();
        super.logAllResponseData();
    }

    public Response getOrders(String xmlBody) {
        super.setHeaders(Headers.buildHeaders("GetOrders"));
        super.setRequestBody(xmlBody);
        return super.sendRequest(Method.POST);
    }
}
