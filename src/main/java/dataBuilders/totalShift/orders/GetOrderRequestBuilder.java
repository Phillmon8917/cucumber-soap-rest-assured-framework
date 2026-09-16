package dataBuilders.totalShift.orders;

import exceptions.NoDataSetException;
import utils.xml.XmlUtils;

import java.util.HashMap;
import java.util.Map;

public class GetOrderRequestBuilder {

    private final Map<String, String> data = new HashMap<>();
    private static final String XML_TEMPLATE_PATH = "requestXmls/totalShiftLeft/orders/getOrder.xml";

    public GetOrderRequestBuilder withId(String id) {
        data.put("id", id);
        return this;
    }

    public String buildXml() {
        if (data.isEmpty()) {
            throw new NoDataSetException();
        }

        String xml = XmlUtils.readFile(XML_TEMPLATE_PATH, data);
        xml = removeUnreplacedPlaceholders(xml);
        return xml;
    }

    private String removeUnreplacedPlaceholders(String xml) {
        xml = xml.replaceAll("<id>\\$\\{id\\}</id>\\s*", "");
        return xml;
    }
}
