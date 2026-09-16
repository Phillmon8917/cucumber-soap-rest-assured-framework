package stepDefinitions.totalShift;

import io.cucumber.java.en.Then;
import sharedContext.SharedStepContext;

public class CommonSteps {

    private final SharedStepContext sharedStepContext;

    public CommonSteps(SharedStepContext sharedStepContext){
        this.sharedStepContext = sharedStepContext;
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int statusCode) {
        this.sharedStepContext.getResponse().then()
                .statusCode(statusCode);
    }
}
