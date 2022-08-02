package au.gov.amsa.configuration.properties;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;

import org.junit.Test;

import com.github.davidmoten.guavamini.Sets;

public class SystemPropertiesConfigurationTest {

    @Test
    public final void test() throws FileNotFoundException {
        System.setProperty("configuration.name", "dave");
        Configuration c = new SystemPropertiesConfiguration();
        assertEquals("dave", c.getStringMandatory("name"));
        assertEquals(Sets.newHashSet("name"), c.getKeyset());
    }

}
