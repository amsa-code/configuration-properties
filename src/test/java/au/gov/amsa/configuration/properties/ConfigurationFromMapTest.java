package au.gov.amsa.configuration.properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class ConfigurationFromMapTest {
    
    @Test
    public void testEmpty( ) {
        Configuration c = ConfigurationFromMap.builder().build();
        assertFalse(c.getKeys().hasMoreElements());
    }
    
    @Test
    public void testPresent() {
        Configuration c = ConfigurationFromMap.add("thing", "cup").build();
        assertEquals("cup", c.getStringMandatory("thing"));
    }

}
