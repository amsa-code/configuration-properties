package au.gov.amsa.configuration.properties;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Obtain configuration via JNDI
 * 
 * @author dxm
 * 
 */
public class JndiConfiguration implements Configuration {

	private final Context context;

	public JndiConfiguration(Map<String, String> map) throws NamingException {
		this.context = new InitialContext(new Hashtable<String, String>(map));
	}

	@Override
	public final Object getProperty(String ctxt, String name) {
		try {
			return context.lookup(name);
		} catch (NamingException e) {
			throw new Error(e);
		}
	}

	@Override
	public final Enumeration<String> getPropertyNames(String context) {
		throw new Error("not implemented yet");
	}

}
