package au.gov.amsa.configuration.properties;

import java.io.PrintStream;
import java.util.Enumeration;

public class ConfigurationWriter {

	public final void write(Configuration configuration, PrintStream out) {
		Enumeration<String> enumeration = configuration.getPropertyNames();
		while (enumeration.hasMoreElements()) {
			String key = enumeration.nextElement();
			out.println(key + "="
					+ ConfigurationUtil.getString(configuration, key));
		}
	}

}
