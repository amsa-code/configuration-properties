package au.gov.amsa.configuration.properties;


import java.io.FileNotFoundException;

import org.junit.Assert;
import org.junit.Test;

public class SystemPropertiesConfigurationTest {

	@Test
	public final void test() throws FileNotFoundException {
		System.setProperty("configuration.name", "dave");
		Configuration c = new SystemPropertiesConfiguration();
		Assert.assertEquals("dave", c.getProperty(null, "name"));
	}

}
