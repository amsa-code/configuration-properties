package au.gov.amsa.configuration.properties;

import java.util.Enumeration;

public class EmptyConfiguration implements Configuration {

	@Override
	public Object getProperty(String context, String name) {
		return null;
	}

	@Override
	public Enumeration<String> getPropertyNames(String context) {
		return new Enumeration<String>() {

			@Override
			public boolean hasMoreElements() {
				return false;
			}

			@Override
			public String nextElement() {
				return null;
			}
		};
	}

}
