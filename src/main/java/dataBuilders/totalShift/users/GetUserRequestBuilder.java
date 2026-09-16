package dataBuilders.totalShift.users;

import exceptions.NoDataSetException;
import utils.xml.XmlUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Builds the XML body for a GetUser SOAP request from a fluent set of fields.
 */
public class GetUserRequestBuilder {

    private Map<String, String> data = new HashMap<>();
    private static final String XML_TEMPLATE_PATH = "requestXmls/totalShiftLeft/users/getUser.xml";

    public GetUserRequestBuilder withId(String id){
        data.put("id", id);
        return this;
    }

    public String buildXml(){
        if (data.isEmpty()){
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
