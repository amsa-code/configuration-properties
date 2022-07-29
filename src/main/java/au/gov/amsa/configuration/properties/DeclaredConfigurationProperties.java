package au.gov.amsa.configuration.properties;

public final class DeclaredConfigurationProperties implements
		ConfigurationDeclaration {

	private final String context;
	private final String name;

	private DeclaredConfigurationProperties(String context, String name) {
		this.context = context;
		this.name = name;
	}

	@Override
	public String getContext() {

		return context;
	}

	@Override
	public String getName() {
		return name;
	}

}
