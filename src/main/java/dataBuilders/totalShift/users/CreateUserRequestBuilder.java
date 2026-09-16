package dataBuilders.totalShift.users;

import exceptions.NoDataSetException;
import utils.xml.XmlUtils;

import java.util.HashMap;
import java.util.Map;

public class CreateUserRequestBuilder {

    private final Map<String, String> data = new HashMap<>();
    private static final String XML_TEMPLATE_PATH = "requestXmls/totalShiftLeft/users/createUser.xml";

    public CreateUserRequestBuilder withName(String name) {
        this.data.put("name", name);
        return this;
    }

    public CreateUserRequestBuilder withEmail(String email) {
        this.data.put("email", email);
        return this;
    }

    public CreateUserRequestBuilder withRole(String role) {
        this.data.put("role", role);
        return this;
    }

    public CreateUserRequestBuilder withAge(String age) {
        this.data.put("age", age);
        return this;
    }

    public Map<String, String> getData() {
        return new HashMap<>(data);
    }

    public CreateUserRequestBuilder reset() {
        this.data.clear();
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
        xml = xml.replaceAll("<email>\\$\\{email\\}</email>\\s*", "");
        xml = xml.replaceAll("<name>\\$\\{name\\}</name>\\s*", "");
        xml = xml.replaceAll("<role>\\$\\{role\\}</role>\\s*", "");
        xml = xml.replaceAll("<age>\\$\\{age\\}</age>\\s*", "");
        return xml;
    }
}
