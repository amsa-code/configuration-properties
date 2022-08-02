package au.gov.amsa.configuration.properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Set;

import org.junit.Test;

import com.github.davidmoten.guavamini.Sets;

public class ConfigurationFromMapTest {

    @Test
    public void testEmpty() {
        Configuration c = ConfigurationFromMap.builder().build();
        assertFalse(c.getKeys().hasMoreElements());
    }

    @Test
    public void testPresent() {
        Configuration c = ConfigurationFromMap.add("thing", "cup").build();
        assertEquals("cup", c.getStringMandatory("thing"));
    }

    @Test
    public void testGetKeys() {
        Set<String> keys = ConfigurationFromMap.add("thing", "cup").add("hi", "there").build().getKeyset();
        assertEquals(Sets.newHashSet("hi", "thing"), keys);
    }

}
