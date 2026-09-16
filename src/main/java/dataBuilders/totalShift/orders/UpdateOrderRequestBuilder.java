package dataBuilders.totalShift.orders;

import exceptions.NoDataSetException;
import utils.xml.XmlUtils;

import java.util.HashMap;
import java.util.Map;

public class UpdateOrderRequestBuilder {
    private final Map<String, String> data = new HashMap<>();
    private static final String XML_TEMPLATE_PATH = "requestXmls/totalShiftLeft/orders/updateOrder.xml";

    public UpdateOrderRequestBuilder withId(String id) {
        data.put("id", id);
        return this;
    }

    public UpdateOrderRequestBuilder withStatus(String status) {
        data.put("status", status);
        return this;
    }

    public UpdateOrderRequestBuilder withQuantity(String quantity) {
        data.put("quantity", quantity);
        return this;
    }

    public UpdateOrderRequestBuilder withNotes(String notes) {
        data.put("notes", notes);
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
        xml = xml.replaceAll("<status>\\$\\{status\\}</status>\\s*", "");
        xml = xml.replaceAll("<quantity>\\$\\{quantity\\}</quantity>\\s*", "");
        xml = xml.replaceAll("<notes>\\$\\{notes\\}</notes>\\s*", "");
        return xml;
    }
}
