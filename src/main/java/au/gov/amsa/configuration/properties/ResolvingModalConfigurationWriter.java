package au.gov.amsa.configuration.properties;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public final class ResolvingModalConfigurationWriter {

	/**
	 * Constructor to prevent inheritance
	 */
	private ResolvingModalConfigurationWriter() {
	}

	public static void main(String[] args) throws FileNotFoundException {
		ConfigurationWriter writer = new ConfigurationWriter();
		PrintStream out = new PrintStream(new File(args[0]));
		writer.write(new ResolvingConfiguration(new ModalConfiguration()), out);
		out.close();
	}

}
