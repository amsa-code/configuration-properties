package au.gov.amsa.configuration.properties;

import java.util.Enumeration;

public class ModalConfigurationFromWebappAndClasspath implements Configuration {

	private static final String CONFIGURATION_PROPERTIES = "/configuration.properties";
	private final ConfigurationUnion c;

	public ModalConfigurationFromWebappAndClasspath() {
		c = new ConfigurationUnion(new ClasspathConfiguration(
				CONFIGURATION_PROPERTIES), new ModalConfigurationFromWebapp());
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
