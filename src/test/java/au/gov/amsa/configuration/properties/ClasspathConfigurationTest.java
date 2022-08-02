package au.gov.amsa.configuration.properties;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ClasspathConfigurationTest {

    @Test
    public void testPresent() {
        assertEquals("cup", new ClasspathConfiguration("/test.properties").getStringMandatory("thing"));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testNotPresent() {
        new ClasspathConfiguration("/not.present");
    }

}
