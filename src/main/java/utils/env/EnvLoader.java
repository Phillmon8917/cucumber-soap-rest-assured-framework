package utils.env;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.experimental.UtilityClass;

/**
 * Resolves configuration secrets from, in order of precedence, OS environment
 * variables, JVM system properties, then {@code env/.env.qa}.
 *
 * <p>The layering lets CI inject values as real environment variables while a
 * developer keeps them in the git-ignored {@code .env.qa} file. The dotenv file
 * is optional, so a missing file is not an error as long as the value is
 * available from one of the other sources.</p>
 */
@UtilityClass
public class EnvLoader {

    private final Dotenv DOTENV = Dotenv.configure()
                                        .directory("envs")
                                        .filename(".env.qa")
                                        .ignoreIfMissing()
                                        .load();

    /**
     * Returns the value bound to the given key.
     *
     * @param key name of the variable to resolve
     * @return the trimmed value
     * @throws IllegalArgumentException if the key is set in none of the sources
     */
    public String getEnvValue(String key) {
        String value = firstNonBlank(
                System.getenv(key),
                System.getProperty(key),
                DOTENV.get(key)
        );

        if (value == null) {
            throw new IllegalArgumentException("Environment variable '" + key + "' was not found");
        }
        return value.trim();
    }

    private String firstNonBlank(String... candidates) {
        for (String candidate : candidates) {
            if (candidate != null && !candidate.isBlank()) {
                return candidate;
            }
        }
        return null;
    }

}