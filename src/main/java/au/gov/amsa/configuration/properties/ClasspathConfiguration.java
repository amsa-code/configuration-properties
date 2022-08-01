package au.gov.amsa.configuration.properties;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Gets configuration from a resource on the classpath
 * 
 */
public class ClasspathConfiguration extends AutoClosingInputStreamConfiguration {

    private static Logger log = LoggerFactory.getLogger(ClasspathConfiguration.class);

    /**
     * Constructor.
     * 
     * @param path resource on classpath
     */
    public ClasspathConfiguration(String path) {
        this(path, ClasspathConfiguration.class);
    }

    /**
     * Constructor.
     * 
     * @param path resource on classpath
     * @param cls  class to obtain classloader from
     */
    public ClasspathConfiguration(String path, Class<?> cls) {
        super(getInputStream(path, cls));
    }

    private static InputStream getInputStream(String path, Class<?> cls) {
        log.info("getting configuration from classpath: " + path + " given class=" + cls.getName());
        InputStream is = cls.getResourceAsStream(path);
        if (is == null)
            throw new RuntimeException("resource not found: " + path);
        else
            return is;
    }
}
