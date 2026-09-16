package api.endpoints.totalShift.orders;

import api.base.BaseApi;
import api.helpers.Headers;
import io.restassured.http.Method;
import io.restassured.response.Response;
import utils.env.EnvLoader;

public class UpdateOrderApi extends BaseApi {

    public UpdateOrderApi() {
        super(EnvLoader.getEnvValue("BASE_URI"));
        super.setBasePath("/soap");
        super.logAllRequestData();
        super.logAllResponseData();
    }

    /**
     * Sends an UpdateOrder SOAP request with the given XML body.
     */
    public Response updateOrder(String xmlBody) {
        super.setHeaders(Headers.buildHeaders("UpdateOrder"));
        super.setRequestBody(xmlBody);
        return super.sendRequest(Method.POST);
    }
}
