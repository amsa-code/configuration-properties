package au.gov.amsa.configuration.properties;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

/**
 * <p>
 * Will obtain configuration values from System properties. Only those keys in
 * System properties that start with a particular prefix will be considered and
 * the prefix will not be part of the ultimate configuration entry. The value of
 * the prefix is determined by the -Dau.gov.amsa.configuration.prefix value or
 * if not set will default to "configuration". Dot is added automatically.
 * </p>
 * <p>
 * For example, if prefix is not set then the prefix is 'configuration' by
 * default and the system property
 * </p>
 * <p>
 * <code>configuration.abc=boo</code>
 * </p>
 * <p>
 * means that configuration will contain the key value pair:
 * </p>
 * <p>
 * <code>abc=boo</code>
 * </p>
 * 
 * @author dxm
 * 
 */
public class SystemPropertiesConfiguration implements Configuration {

	private static Logger log = Logger
			.getLogger(SystemPropertiesConfiguration.class);
	private String prefix;

	public SystemPropertiesConfiguration() {
		prefix = System.getProperty("au.gov.amsa.configuration.prefix");
		if (prefix == null)
			prefix = "configuration";
		log.info("using prefix=" + prefix);
	}

	@Override
	public final Object getProperty(String context, String name) {
		String key = prefix + "." + name;
		if (context != null)
			key = prefix + "." + context + "." + name;
		return System.getProperty(key);
	}

	@Override
	public final Enumeration<String> getPropertyNames(String context) {
		//only report those properties that start with the prefix (and dot)
		final Map<String, String> map = new HashMap<String, String>();
		for (Entry<Object, Object> entry : System.getProperties().entrySet())
			if (entry.getKey().toString().startsWith(prefix + ".")) {
				String value = (String) entry.getValue();
				String key = (String) entry.getKey();
				String keyPart = key.substring(prefix.length() + 1);
				map.put(keyPart, value);
			}
		Enumeration<String> e = new Enumeration<String>() {
			private final Iterator<String> it = map.keySet().iterator();

			@Override
			public boolean hasMoreElements() {
				return it.hasNext();
			}

			@Override
			public String nextElement() {
				return it.next();
			}
		};
		return e;
	}
}
