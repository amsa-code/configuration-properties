package au.gov.amsa.configuration.properties;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.NoSuchElementException;

import org.junit.Test;

public class EmptyConfigurationTest {

    @Test
    public void testEmpty() {
        Configuration c = new EmptyConfiguration();
        assertFalse(c.getString("hello").isPresent());
        assertTrue(c.getKeyset().isEmpty());
    }

    @Test(expected = NoSuchElementException.class)
    public void testEmptyEnumThrows() {
        Configuration c = new EmptyConfiguration();
        c.getKeys().nextElement();
    }

}
