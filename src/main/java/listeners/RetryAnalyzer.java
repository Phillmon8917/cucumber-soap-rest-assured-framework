package listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import utils.env.EnvLoader;

/**
 * Re-runs a failed scenario up to {@code MAX_RETRY_COUNT} times.
 *
 * <p>Restful-Booker is a shared public sandbox on a free host, so an occasional
 * timeout or 5xx is expected. Bounded retries keep those transient failures
 * from breaking an otherwise green run; a genuinely broken scenario still fails
 * once the budget is exhausted. TestNG creates one instance per test method, so
 * the counter is naturally per-scenario.</p>
 */
public class RetryAnalyzer implements IRetryAnalyzer {

    private final int maxRetryCount = Integer.parseInt(EnvLoader.getEnvValue("MAX_RETRY_COUNT"));
    private int attempts;

    /**
     * Decides whether the just-failed test should run again.
     *
     * @param result the failed test result
     * @return {@code true} while retries remain, otherwise {@code false}
     */
    @Override
    public boolean retry(ITestResult result) {
        if (attempts < maxRetryCount) {
            attempts++;
            return true;
        }
        return false;
    }
}