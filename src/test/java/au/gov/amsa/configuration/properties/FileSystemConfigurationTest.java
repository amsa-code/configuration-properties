package au.gov.amsa.configuration.properties;

import static org.junit.Assert.assertEquals;

import java.io.UncheckedIOException;

import org.junit.Test;

public class FileSystemConfigurationTest {

    @Test
    public void testPresent() {
        Configuration c = new FileSystemConfiguration("src/test/resources/test.properties");
        assertEquals("cup", c.getStringMandatory("thing"));
    }

    @Test(expected = UncheckedIOException.class)
    public void testNotPresent() {
        new FileSystemConfiguration("src/test/resources/not.present");
    }

}
