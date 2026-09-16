package hooks;

import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import org.apache.commons.io.FileUtils;
import sharedContext.SharedStepContext;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Cucumber lifecycle hooks that run once per test JVM.
 */
public class Hooks {

    private final SharedStepContext sharedStepContext;

    public Hooks(SharedStepContext sharedStepContext) {
        this.sharedStepContext = sharedStepContext;
    }

    private static final Path ALLURE_RESULTS = Paths.get("allure-results");

    /**
     * Clears stale Allure result files so a run's report reflects only that run.
     */
    @BeforeAll
    public static void clearPreviousAllureResults() {
        if (!Files.exists(ALLURE_RESULTS)) {
            return;
        }
        try {
            FileUtils.cleanDirectory(ALLURE_RESULTS.toFile());
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to clear " + ALLURE_RESULTS, e);
        }
    }

    @Before
    public void setUp(){
        sharedStepContext.reset();
    }
}