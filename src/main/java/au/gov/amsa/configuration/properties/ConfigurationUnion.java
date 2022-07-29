package au.gov.amsa.configuration.properties;

import java.util.Enumeration;

/**
 * Joins two configurations to provide one effective configuration file. The
 * first of the two configurations overrides the second.
 * 
 * @author dxm
 * 
 */
public class ConfigurationUnion implements Configuration {

	private final Configuration c1;
	private final Configuration c2;

	/**
	 * The first of the two configurations overrides the second.
	 * 
	 * @param c1
	 * @param c2
	 */
	public ConfigurationUnion(Configuration c1, Configuration c2) {
		this.c1 = c1;
		this.c2 = c2;
	}

	@Override
	public final Object getProperty(String context, String name) {
		if (c1.getProperty(context, name) == null)
			return c2.getProperty(context, name);
		else
			return c1.getProperty(context, name);
	}

	@Override
	public final Enumeration<String> getPropertyNames(String context) {
		final Enumeration<String> e1 = c1.getPropertyNames(context);
		final Enumeration<String> e2 = c2.getPropertyNames(context);
		return new Enumeration<String>() {

			@Override
			public boolean hasMoreElements() {
				return e1.hasMoreElements() || e2.hasMoreElements();
			}

			@Override
			public String nextElement() {
				if (e1.hasMoreElements())
					return e1.nextElement();
				else
					return e2.nextElement();
			}
		};
	}
}