package au.gov.amsa.configuration.properties;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Enumeration;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Loads configuration from an InputStream
 * 
 * @author dxm
 * 
 */
public class AutoClosingInputStreamConfiguration implements Configuration {
	private final Properties props;
	private static Logger log = LoggerFactory
			.getLogger(AutoClosingInputStreamConfiguration.class);

	public AutoClosingInputStreamConfiguration(InputStream is) {
		props = new Properties();
		try {
			props.load(is);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		} finally {
		    try {
                is.close();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
		}
	}

	@Override
	public final String getProperty(String name) {
		String o = props.getProperty(name);
		if (o == null)
			log.info("property " + name + " not found");
		else {
			Object value = o;
			if (name.contains("password"))
				value = "*****";
			log.info("property " + name + "=" + value);
		}
		return o;
	}

	@Override
	public final Enumeration<String> getPropertyNames() {

		final Enumeration<Object> e = props.keys();
		return new Enumeration<String>() {

			@Override
			public boolean hasMoreElements() {
				return e.hasMoreElements();
			}

			@Override
			public String nextElement() {
				return (String) e.nextElement();
			}
		};

	}

}
