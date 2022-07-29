package au.gov.amsa.configuration.properties;

import java.util.Enumeration;
import java.util.Map;

public class ParameterizedConfiguration implements Configuration {

	private final Configuration configuration;
	private Map<String, String> replacements;

	public ParameterizedConfiguration(Configuration configuration,
			Map<String, String> replacements) {
		this.configuration = configuration;
		this.replacements = replacements;
	}

	@Override
	public final Object getProperty(String context, String name) {
		return configuration.getProperty(context, getSubstituted(name));
	}

	public final String getSubstituted(String name) {
		String result = name;
		for (String key : replacements.keySet())
			result = result.replace(key, replacements.get(key));
		return result;
	}

	public final Map<String, String> getReplacements() {
		return replacements;
	}

	public final void setReplacements(Map<String, String> replacements) {
		this.replacements = replacements;
	}

	@Override
	public final Enumeration<String> getPropertyNames(String context) {
		throw new Error("not implemented yet");
	}

}
