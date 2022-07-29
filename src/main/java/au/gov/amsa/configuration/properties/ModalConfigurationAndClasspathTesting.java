package au.gov.amsa.configuration.properties;

import java.util.Enumeration;

public class ModalConfigurationAndClasspathTesting implements Configuration {

	private static final String CONFIGURATION_PROPERTIES = "/configuration.properties";
	private final ConfigurationUnion c;

	public ModalConfigurationAndClasspathTesting() {
		String mode = System.getProperty("mode");
		if (mode == null) // default to dev
			mode = "dev";
		c = new ConfigurationUnion(new ClasspathConfiguration(
				CONFIGURATION_PROPERTIES), new ClasspathConfiguration("/"
				+ mode + "/configuration.properties"));
	}

	@Override
	public Object getProperty(String context, String name) {
		return c.getProperty(context, name);
	}

	@Override
	public Enumeration<String> getPropertyNames(String context) {
		return c.getPropertyNames(context);
	}
}
