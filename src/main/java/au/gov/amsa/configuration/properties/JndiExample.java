package au.gov.amsa.configuration.properties;

import java.util.Hashtable;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

public final class JndiExample {

	/**
	 * Constructor to prevent inheritance
	 */
	private JndiExample() {
	}

	public static void main(String[] rgstring) {
		try {
			// Create the initial context. The environment
			// information specifies the JNDI provider to use
			// and the initial URL to use (in our case, a
			// directory in URL form -- file:///...).
			Hashtable<String, String> hashtableEnvironment = new Hashtable<String, String>();
			hashtableEnvironment.put(Context.INITIAL_CONTEXT_FACTORY,
					"com.sun.jndi.fscontext.RefFSContextFactory");
			hashtableEnvironment.put(Context.PROVIDER_URL, rgstring[0]);
			Context context = new InitialContext(hashtableEnvironment);
			// If you provide no other command line arguments,
			// list all of the names in the specified context and
			// the objects they are bound to.
			if (rgstring.length == 1) {
				NamingEnumeration<Binding> namingenumeration = context
						.listBindings("");
				while (namingenumeration.hasMore()) {
					Binding binding = namingenumeration.next();
					System.out.println(binding.getName() + " "
							+ binding.getObject());
				}
			} else {
				// Otherwise, list the names and bindings for the
				// specified arguments.
				for (int i = 1; i < rgstring.length; i++) {
					Object object = context.lookup(rgstring[i]);
					System.out.println(rgstring[i] + " " + object);
				}
			}
			context.close();
		} catch (NamingException namingexception) {
			namingexception.printStackTrace();
		}
	}
}