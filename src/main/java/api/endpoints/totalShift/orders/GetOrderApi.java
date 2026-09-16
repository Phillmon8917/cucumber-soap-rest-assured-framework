package api.endpoints.totalShift.orders;

import api.base.BaseApi;
import api.helpers.Headers;
import io.restassured.http.Method;
import io.restassured.response.Response;
import utils.env.EnvLoader;

public class GetOrderApi extends BaseApi {

    public GetOrderApi() {
        super(EnvLoader.getEnvValue("BASE_URI"));
        super.setBasePath("/soap");
        super.logAllRequestData();
        super.logAllResponseData();
    }

    /**
     * Sends a GetOrder SOAP request with the given XML body.
     */
    public Response getOrder(String xmlBody) {
        super.setHeaders(Headers.buildHeaders("GetOrder"));
        super.setRequestBody(xmlBody);
        return super.sendRequest(Method.POST);
    }
}
