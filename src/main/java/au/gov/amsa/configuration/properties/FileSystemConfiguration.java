package au.gov.amsa.configuration.properties;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Loads a configuration from the file system
 * 
 */
public final class FileSystemConfiguration extends AutoClosingInputStreamConfiguration {

    private static Logger log = LoggerFactory.getLogger(FileSystemConfiguration.class);

    public FileSystemConfiguration(String filename) {
        super(getFileInputStream(filename));
    }

    private static InputStream getFileInputStream(String filename) {
        log.info("reading configuration from file: " + filename);
        File file = new File(filename);
        log.info("full filename=" + file.getAbsolutePath());
        if (!file.exists())
            log.error("file does not exist: " + file.getAbsolutePath());
        try {
            return new FileInputStream(filename);
        } catch (FileNotFoundException e) {
            throw new UncheckedIOException(e);
        }
    }

    public FileSystemConfiguration(URL url) throws FileNotFoundException, URISyntaxException {
        super(new BufferedInputStream(new FileInputStream(new File(url.toURI()))));
    }

}
