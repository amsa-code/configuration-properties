package au.gov.amsa.configuration.properties;

import java.util.Set;

public class DeclaredConfigurationRestrictor implements
		ConfigurationViaDeclaration {

	private final Configuration configuration;
	private final Set<ConfigurationDeclaration> declarations;

	public DeclaredConfigurationRestrictor(
			Set<ConfigurationDeclaration> declarations,
			Configuration configuration) {
		this.declarations = declarations;
		this.configuration = configuration;
	}

	@Override
	public final Object getProperty(ConfigurationDeclaration declaration) {
		return configuration.getProperty(declaration.getContext(),
				declaration.getName());
	}

}
