package stepDefinitions.totalShift;

import io.cucumber.java.en.Then;
import sharedContext.SharedStepContext;

public class CommonSteps {

    private final SharedStepContext sharedStepContext;

    public CommonSteps(SharedStepContext sharedStepContext){
        this.sharedStepContext = sharedStepContext;
    }

    /**
     * Asserts that the last response's HTTP status code matches the given value.
     */
    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int statusCode) {
        this.sharedStepContext.getResponse().then()
                .statusCode(statusCode);
    }
}
