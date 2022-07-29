package au.gov.amsa.configuration.properties;

import java.util.Set;

public class DeclaredConfiguration implements ConfigurationViaDeclaration {

	private final Configuration configuration;
	private final Set<ConfigurationDeclaration> declarations;

	public DeclaredConfiguration(Set<ConfigurationDeclaration> declarations,
			Configuration configuration) {
		this.declarations = declarations;
		this.configuration = configuration;
		for (ConfigurationDeclaration declaration : declarations) {
			if (configuration.getProperty(declaration.getContext(), declaration
					.getName()) == null)
				throw new Error("(context,name)=(" + declaration.getContext()
						+ "," + declaration.getName()
						+ ") not found in configuration!");
		}
	}

	@Override
	public final Object getProperty(ConfigurationDeclaration dec) {
		for (ConfigurationDeclaration declaration : declarations) {
			if (declaration.getContext().equals(dec.getContext())
					&& declaration.getName().equals(dec.getName())) {
				return configuration.getProperty(dec.getContext(), dec
						.getName());
			}
		}
		String resultingName = dec.getName();
		if (configuration instanceof ParameterizedConfiguration)
			resultingName = ((ParameterizedConfiguration) configuration)
					.getSubstituted(dec.getName());
		throw new Error("This context,name pair not declared: ("
				+ dec.getContext() + ", " + resultingName + ")");
	}

}
