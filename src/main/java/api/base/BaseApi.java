package api.base;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.env.EnvLoader;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseApi {

    private static final Logger logger = LoggerFactory.getLogger(BaseApi.class);
    private RequestSpecification requestSpecification;
    Map<String, Object> headers = new HashMap<>();
    private final String baseUri;
    private final HttpClientConfig httpClientConfig;
    private String basePath;

    public BaseApi(String baseUri) {

        this.baseUri = baseUri;
        this.httpClientConfig = HttpClientConfig.httpClientConfig()
                                                            .setParam("http.connection.timeout", Integer.parseInt(EnvLoader.getEnvValue("CONNECTION_TIMEOUT")))
                                                            .setParam("http.socket.timeout", Integer.parseInt(EnvLoader.getEnvValue("SOCKET_TIMEOUT")));

        this.requestSpecification = RestAssured.given()
                                               .and().baseUri(baseUri)
                                               .and().config(RestAssured.config().httpClient(httpClientConfig))
                                               .and().filter(new AllureRestAssured());

        logger.info("Initialized the api client for base URI: {}", baseUri);
    }

    /**
     * Rebuilds the request specification from scratch, reapplying the base path if one was set.
     */
    protected void buildRequestSpecification() {
        this.requestSpecification = RestAssured.given()
                                               .and().baseUri(baseUri)
                                               .and().config(RestAssured.config().httpClient(httpClientConfig))
                                               .and().filter(new AllureRestAssured());

        if (this.basePath != null) {
            this.requestSpecification.basePath(this.basePath);
        }
    }

    /**
     * Sets the base path appended to the base URI for every request sent by this client.
     */
    protected void setBasePath(String basePath) {
        this.basePath = basePath;
        this.requestSpecification.basePath(this.basePath);
        logger.info("Set the best path, {}", basePath);
    }

    /**
     * Binds a value to a named path parameter in the request URL.
     */
    protected void setPathParam(String parameterName, Object value) {
        this.requestSpecification.pathParam(parameterName, value);
        logger.info("Set parameter {}", parameterName);
    }

    /**
     * Sets the raw XML body to be sent with the request.
     */
    protected void setRequestBody(String xmlRequestBody) {
        this.requestSpecification.body(xmlRequestBody);
        logger.info("Set the request body");
    }

    /**
     * Sets the Content-Type header for the request.
     */
    protected void setContentType(ContentType contentType) {
        this.requestSpecification.contentType(contentType);
        logger.info("Set content type {}", contentType);
    }

    /**
     * Replaces any previously set headers with the given ones.
     */
    protected void setHeaders(Map<String, ?> requestHeaders){
        this.headers.clear();
        this.headers.putAll(requestHeaders);
        this.requestSpecification.headers(this.headers);
        logger.info("Set headers: {}", headers.toString());
    }

    /**
     * Applies HTTP Basic authentication to the request.
     */
    protected void setBasicAuth(String username, String password) {
        this.requestSpecification.auth().basic(username, password);
        logger.info("Set basic auth");
    }

    /**
     * Applies Bearer token authentication to the request.
     */
    protected void setBearerAuth(String token) {
        this.requestSpecification.auth().oauth2(token);
        logger.info("Set bearer auth");
    }

    /**
     * Logs the full request (headers, body, params) when it is sent.
     */
    protected void logAllRequestData() {
        this.requestSpecification.filter(new RequestLoggingFilter());
    }

    /**
     * Logs only the given detail of the request when it is sent.
     */
    protected void logSpecificRequestData(LogDetail logDetail) {
        this.requestSpecification.filter(new RequestLoggingFilter(logDetail));
    }

    /**
     * Logs the full response (headers, body, status) once it is received.
     */
    protected void logAllResponseData() {
        this.requestSpecification.filter(new ResponseLoggingFilter());
    }

    /**
     * Logs only the given detail of the response once it is received.
     */
    protected void logSpecificResponseData(LogDetail logDetail) {
        this.requestSpecification.filter(new ResponseLoggingFilter(logDetail));
    }

    /**
     * Sends the built request using the given HTTP method and returns the response.
     */
    protected Response sendRequest(Method methodType) {
        RequestSpecification when = this.requestSpecification.when();
        return switch (methodType) {
            case GET -> when.get();
            case PUT -> when.put();
            case POST -> when.post();
            case DELETE -> when.delete();
            case PATCH -> when.patch();
            default -> throw new IllegalArgumentException("HTTP method not supported: " + methodType);
        };
    }
}
