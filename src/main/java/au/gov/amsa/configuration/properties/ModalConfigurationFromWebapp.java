package au.gov.amsa.configuration.properties;

import java.io.FileNotFoundException;
import java.util.Enumeration;

public class ModalConfigurationFromWebapp implements Configuration {

	private FileSystemConfiguration c;

	public ModalConfigurationFromWebapp() {
		String mode = System.getProperty("mode");
		try {
			c = new FileSystemConfiguration(
					"../webapps/configuration/WEB-INF/classes/" + mode
							+ "/configuration.properties");
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
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
