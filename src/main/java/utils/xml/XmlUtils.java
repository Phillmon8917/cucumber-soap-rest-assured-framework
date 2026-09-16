package utils.xml;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@UtilityClass
public class XmlUtils {

    public String readFile(String resourcePath) {

        try (InputStream in = XmlUtils.class.getClassLoader()
                                            .getResourceAsStream(resourcePath)) {

            if (in == null) {
                throw new IllegalArgumentException(
                        "XML template not found on classpath: " + resourcePath);
            }

            return new String(in.readAllBytes(), StandardCharsets.UTF_8);

        } catch (IOException e) {
            throw new UncheckedIOException(
                    "Failed to read XML template: " + resourcePath, e);
        }
    }

    public String readFile(String resourcePath, Map<String, String> replacements) {
        String content = readFile(resourcePath);

        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            content = content.replace("${" + entry.getKey() + "}", entry.getValue());
        }

        return content;
    }
}
