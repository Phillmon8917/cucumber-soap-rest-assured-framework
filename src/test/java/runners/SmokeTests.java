package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import listeners.RetryAnalyzer;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepDefinitions", "hooks"},
        plugin = {"io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm", "pretty"},
        tags = "@smoke and not @wip and not @skip"
)
public class SmokeTests extends AbstractTestNGCucumberTests {

    /**
     * Supplies the smoke-tagged scenarios to run, sequentially.
     */
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }

    /**
     * Runs a single scenario, retrying it on failure via {@link RetryAnalyzer}.
     */
    @Test(description = "Smoke Tests - For local single scenario run",
            dataProvider = "scenarios",
            retryAnalyzer = RetryAnalyzer.class
    )
    public void runScenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
        super.runScenario(pickleWrapper, featureWrapper);
    }
}