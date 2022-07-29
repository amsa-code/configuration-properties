package au.gov.amsa.configuration.properties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class ConfigurationExtended implements Configuration {

	private final Configuration configuration;

	public ConfigurationExtended(Configuration configuration) {
		super();
		this.configuration = configuration;
	}

	@Override
	public final Object getProperty(String context, String name) {
		return configuration.getProperty(context, name);
	}

	@Override
	public final Enumeration<String> getPropertyNames(String context) {
		return configuration.getPropertyNames(context);
	}

	/**
	 * Returns false if property not found. Throws an error if property not
	 * Boolean or String.
	 * 
	 * @param context
	 * @param name
	 * @return
	 */
	public final Boolean getPropertyBoolean(String context, String name) {
		Object o = getProperty(context, name);
		if (o == null)
			return false;
		else if (o instanceof Boolean)
			return (Boolean) o;
		else if (o instanceof String)
			return "true".equalsIgnoreCase((String) getProperty(context, name));
		else
			throw new Error("property not a Boolean");
	}

	/**
	 * returns null if property not found
	 * 
	 * @param context
	 * @param name
	 * @return
	 */
	public final String getPropertyString(String context, String name) {
		Object o = getProperty(context, name);
		if (o == null)
			return null;
		else if (o instanceof String)
			return (String) o;
		else
			throw new Error("property not a String");
	}

	/**
	 * Returns the property names ordered alphabetically
	 * 
	 * @param context
	 * @return
	 */
	public final Enumeration<String> getPropertyNamesSorted(String context) {
		final List<String> list = new ArrayList<String>();
		Enumeration<String> e = configuration.getPropertyNames(context);
		while (e.hasMoreElements())
			list.add(e.nextElement());
		Collections.sort(list);

		return new Enumeration<String>() {

			private int i = 0;

			@Override
			public boolean hasMoreElements() {
				return i < list.size();
			}

			@Override
			public String nextElement() {
				i++;
				return list.get(i - 1);
			}
		};
	}
}
