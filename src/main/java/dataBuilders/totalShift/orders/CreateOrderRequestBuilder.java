package dataBuilders.totalShift.orders;

import exceptions.NoDataSetException;
import utils.xml.XmlUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Builds the XML body for a CreateOrder SOAP request from a fluent set of fields.
 */
public class CreateOrderRequestBuilder {

    private final Map<String, String> data = new HashMap<>();
    private static final String XML_TEMPLATE_PATH = "requestXmls/totalShiftLeft/orders/createOrder.xml";

    public CreateOrderRequestBuilder withUserId(String userId) {
        this.data.put("userId", userId);
        return this;
    }

    public CreateOrderRequestBuilder withProductId(String productId) {
        this.data.put("productId", productId);
        return this;
    }

    public CreateOrderRequestBuilder withQuantity(String quantity) {
        this.data.put("quantity", quantity);
        return this;
    }

    public CreateOrderRequestBuilder withStatus(String status) {
        this.data.put("status", status);
        return this;
    }

    public CreateOrderRequestBuilder withNotes(String notes) {
        this.data.put("notes", notes);
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
        xml = xml.replaceAll("<user_id>\\$\\{userId\\}</user_id>\\s*", "");
        xml = xml.replaceAll("<product_id>\\$\\{productId\\}</product_id>\\s*", "");
        xml = xml.replaceAll("<quantity>\\$\\{quantity\\}</quantity>\\s*", "");
        xml = xml.replaceAll("<status>\\$\\{status\\}</status>\\s*", "");
        xml = xml.replaceAll("<notes>\\$\\{notes\\}</notes>\\s*", "");
        return xml;
    }
}
