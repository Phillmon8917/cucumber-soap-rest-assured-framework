package api.endpoints.totalShift.users;

import api.base.BaseApi;
import api.helpers.Headers;
import io.restassured.http.Method;
import io.restassured.response.Response;
import utils.env.EnvLoader;

public class CreateUserApi extends BaseApi {

    public CreateUserApi(){
        super(EnvLoader.getEnvValue("BASE_URI"));
        super.setBasePath("/soap");
        super.logAllRequestData();
        super.logAllResponseData();
    }

    /**
     * Sends a CreateUser SOAP request with the given XML body.
     */
    public Response CreateANewUser(String xmlBody){
        super.buildRequestSpecification();
        super.setRequestBody(xmlBody);
        super.setHeaders(Headers.buildHeaders("CreateUser"));
        return super.sendRequest(Method.POST);
    }
}
