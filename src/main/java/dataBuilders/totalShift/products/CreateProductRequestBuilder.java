package dataBuilders.totalShift.products;

import exceptions.NoDataSetException;
import utils.xml.XmlUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Builds the XML body for a CreateProduct SOAP request from a fluent set of fields.
 */
public class CreateProductRequestBuilder {

    private final Map<String, String> data = new HashMap<>();
    private static final String XML_TEMPLATE_PATH = "requestXmls/totalShiftLeft/products/createProduct.xml";

    public CreateProductRequestBuilder withName(String name) {
        this.data.put("name", name);
        return this;
    }

    public CreateProductRequestBuilder withPrice(String price) {
        this.data.put("price", price);
        return this;
    }

    public CreateProductRequestBuilder withDescription(String description) {
        this.data.put("description", description);
        return this;
    }

    public CreateProductRequestBuilder withStock(String stock) {
        this.data.put("stock", stock);
        return this;
    }

    public CreateProductRequestBuilder withCategory(String category) {
        this.data.put("category", category);
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
        xml = xml.replaceAll("<name>\\$\\{name\\}</name>\\s*", "");
        xml = xml.replaceAll("<price>\\$\\{price\\}</price>\\s*", "");
        xml = xml.replaceAll("<description>\\$\\{description\\}</description>\\s*", "");
        xml = xml.replaceAll("<stock>\\$\\{stock\\}</stock>\\s*", "");
        xml = xml.replaceAll("<category>\\$\\{category\\}</category>\\s*", "");
        return xml;
    }
}
