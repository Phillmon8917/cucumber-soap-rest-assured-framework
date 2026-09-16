package api.endpoints.totalShift.users;

import api.base.BaseApi;
import api.helpers.Headers;
import io.restassured.http.Method;
import io.restassured.response.Response;
import utils.env.EnvLoader;

public class UpdateUserApi extends BaseApi {
    public UpdateUserApi() {
        super(EnvLoader.getEnvValue("BASE_URI"));
        super.setBasePath("/soap");
        super.logAllRequestData();
        super.logAllRequestData();
    }

    public Response updateUser(String xmlBody){
        super.setHeaders(Headers.buildHeaders("UpdateUser"));
        super.setRequestBody(xmlBody);
        return super.sendRequest(Method.POST);
    }
}
