package au.gov.amsa.configuration.properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class ConfigurationUnionTest {
    
    @Test
    public void test() {
        Configuration c1 = ConfigurationFromMap.add("thing", "cup").build();
        Configuration c2 = ConfigurationFromMap.add("thing", "glass").add("stuff", "table").build();
        Configuration c = new ConfigurationUnion(c1, c2);
        assertEquals("cup", c.getStringMandatory("thing"));
        assertEquals("table", c.getStringMandatory("stuff"));
        assertFalse(c.getString("not.present").isPresent());
    }

}
