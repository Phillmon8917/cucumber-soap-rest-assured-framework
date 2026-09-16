package dataBuilders.totalShift.products;

import exceptions.NoDataSetException;
import utils.xml.XmlUtils;

import java.util.HashMap;
import java.util.Map;

public class UpdateProductRequestBuilder {
    private final Map<String, String> data = new HashMap<>();
    private static final String XML_TEMPLATE_PATH = "requestXmls/totalShiftLeft/products/updateProduct.xml";

    public UpdateProductRequestBuilder withId(String id) {
        data.put("id", id);
        return this;
    }

    public UpdateProductRequestBuilder withName(String name) {
        data.put("name", name);
        return this;
    }

    public UpdateProductRequestBuilder withPrice(String price) {
        data.put("price", price);
        return this;
    }

    public UpdateProductRequestBuilder withDescription(String description) {
        data.put("description", description);
        return this;
    }

    public UpdateProductRequestBuilder withStock(String stock) {
        data.put("stock", stock);
        return this;
    }

    public UpdateProductRequestBuilder withCategory(String category) {
        data.put("category", category);
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
        xml = xml.replaceAll("<name>\\$\\{name\\}</name>\\s*", "");
        xml = xml.replaceAll("<price>\\$\\{price\\}</price>\\s*", "");
        xml = xml.replaceAll("<description>\\$\\{description\\}</description>\\s*", "");
        xml = xml.replaceAll("<stock>\\$\\{stock\\}</stock>\\s*", "");
        xml = xml.replaceAll("<category>\\$\\{category\\}</category>\\s*", "");
        return xml;
    }
}
