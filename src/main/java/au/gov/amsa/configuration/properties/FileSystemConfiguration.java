package au.gov.amsa.configuration.properties;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Loads a configuration from the file system
 * 
 * @author dxm
 * 
 */
public class FileSystemConfiguration extends AutoClosingInputStreamConfiguration {

	private static Logger log = LoggerFactory.getLogger(FileSystemConfiguration.class);

	public FileSystemConfiguration(String filename)
			throws FileNotFoundException {
		super(getFileInputStream(filename));
	}

	private static InputStream getFileInputStream(String filename)
			throws FileNotFoundException {
		log.info("reading configuration from file: " + filename);
		File file = new File(filename);
		log.info("full filename=" + file.getAbsolutePath());
		if (!file.exists())
			log.error("file does not exist: " + file.getAbsolutePath());
		return new FileInputStream(filename);
	}

	public FileSystemConfiguration(URL url) throws FileNotFoundException,
			URISyntaxException {
		super(new BufferedInputStream(new FileInputStream(new File(url.toURI()))));
	}

}
