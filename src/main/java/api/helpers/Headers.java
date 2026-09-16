package api.helpers;

import lombok.experimental.UtilityClass;

import java.util.Map;

@UtilityClass
public class Headers {

    public Map<String, String> buildHeaders(String soapAction) {
        return Map.of(
                "Content-Type", "text/xml; charset=utf-8",
                "SOAPAction", soapAction
        );
    }
}
