package dataBuilders.totalShift.users;

import exceptions.NoDataSetException;
import utils.xml.XmlUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Builds the XML body for an UpdateUser SOAP request from a fluent set of fields.
 */
public class UpdateUserRequestBuilder {
    private final Map<String, String> data = new HashMap<>();
    private static final String XML_TEMPLATE_PATH = "requestXmls/totalShiftLeft/users/updateUser.xml";

    public UpdateUserRequestBuilder withId(String id){
        data.put("id", id);
        return this;
    }

    public UpdateUserRequestBuilder withName(String name){
        data.put("name", name);
        return this;
    }

    public UpdateUserRequestBuilder withEmail(String email){
        data.put("email", email);
        return this;
    }

    public UpdateUserRequestBuilder withRole(String role){
        data.put("role", role);
        return this;
    }

    public UpdateUserRequestBuilder withAge(String age){
        data.put("age", age);
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
        xml = xml.replaceAll("<name>\\$\\{name\\}</name>\\s*", "");
        xml = xml.replaceAll("<email>\\$\\{email\\}</email>\\s*", "");
        xml = xml.replaceAll("<role>\\$\\{role\\}</role>\\s*", "");
        xml = xml.replaceAll("<age>\\$\\{age\\}</age>\\s*", "");
        return xml;
    }
}
