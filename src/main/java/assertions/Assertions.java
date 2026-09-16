package assertions;


import io.restassured.path.xml.XmlPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

public class Assertions {

    private static final Logger logger = LoggerFactory.getLogger(Assertions.class);
    private final XmlPath xmlPath;
    private final String elementName;

    public Assertions(String responseBody, String elementName){
        this.xmlPath = new XmlPath(responseBody);
        this.elementName = elementName;
    }

    private String buildXPath(String fieldName){
        String xpath = String.format("**.find { it.name() == '%s' }.%s", elementName, fieldName);
        logger.debug("Xpath built {}", xpath);
        return xpath;
    }

    public Assertions assertFieldNotNull(String fieldName){
        String actualValue = getActualValue(fieldName);

        Assert.assertNotNull(actualValue);
        return this;
    }

    public Assertions assertFieldEquals(String fieldName, String expectedValue){
        String actualValue = getActualValue(fieldName);

        assertFieldNotNull(fieldName);
        Assert.assertEquals(actualValue, expectedValue);
        return this;
    }

    public Assertions assertFieldContains(String fieldName, String subString){
        String actualValue = getActualValue(fieldName);

        assertFieldNotNull(fieldName);
        Assert.assertTrue(actualValue.contains(subString));
        return this;
    }

    private String getActualValue(String fieldName) {
        String fieldPath = buildXPath(fieldName);
        return xmlPath.getString(fieldPath);
    }
}
