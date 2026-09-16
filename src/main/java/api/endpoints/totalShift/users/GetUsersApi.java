package api.endpoints.totalShift.users;

import api.base.BaseApi;
import api.helpers.Headers;
import io.restassured.http.Method;
import io.restassured.response.Response;
import utils.env.EnvLoader;

public class GetUsersApi extends BaseApi {

    public GetUsersApi() {
        super(EnvLoader.getEnvValue("BASE_URI"));
        super.setBasePath("/soap");
        super.logAllRequestData();
        super.logAllResponseData();
    }

    public Response getUsers(String xmlBody){
        super.setHeaders(Headers.buildHeaders("GetUsers"));
        super.setRequestBody(xmlBody);
        return super.sendRequest(Method.POST);
    }
}
