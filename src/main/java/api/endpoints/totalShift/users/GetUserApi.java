package api.endpoints.totalShift.users;

import api.base.BaseApi;
import api.helpers.Headers;
import io.restassured.http.Method;
import io.restassured.response.Response;
import utils.env.EnvLoader;

public class GetUserApi extends BaseApi {

    public GetUserApi() {
        super(EnvLoader.getEnvValue("BASE_URI"));
        super.setBasePath("/soap");
        super.logAllRequestData();
        super.logAllResponseData();
    }

    /**
     * Sends a GetUser SOAP request with the given XML body.
     */
    public Response getUser(String xmlBody){
        super.setHeaders(Headers.buildHeaders("GetUser"));
        super.setRequestBody(xmlBody);
        return super.sendRequest(Method.POST);
    }
}
