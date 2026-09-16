package dataBuilders.totalShift.products;

import exceptions.NoDataSetException;
import utils.xml.XmlUtils;

import java.util.HashMap;
import java.util.Map;

public class GetProductsRequestBuilder {
    private final Map<String, String> data = new HashMap<>();
    private static final String XML_TEMPLATE_PATH = "requestXmls/totalShiftLeft/products/getProducts.xml";

    public GetProductsRequestBuilder withPage(String page) {
        this.data.put("page", page);
        return this;
    }

    public GetProductsRequestBuilder withLimit(String limit) {
        this.data.put("limit", limit);
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
        xml = xml.replaceAll("<page>\\$\\{page\\}</page>\\s*", "");
        xml = xml.replaceAll("<limit>\\$\\{limit\\}</limit>\\s*", "");
        return xml;
    }
}
