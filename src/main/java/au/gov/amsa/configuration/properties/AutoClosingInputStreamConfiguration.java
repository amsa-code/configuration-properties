package au.gov.amsa.configuration.properties;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Enumeration;
import java.util.Optional;
import java.util.Properties;

/**
 * Loads configuration from an InputStream
 * 
 */
public class AutoClosingInputStreamConfiguration implements Configuration {
    private final Properties props;

    public AutoClosingInputStreamConfiguration(InputStream is) {
        props = new Properties();
        try {
            props.load(is);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }

    @Override
    public final Optional<String> getString(String name) {
        return Optional.ofNullable(props.getProperty(name));
    }

    @Override
    public final Enumeration<String> getKeys() {

        final Enumeration<Object> e = props.keys();
        return new Enumeration<String>() {

            @Override
            public boolean hasMoreElements() {
                return e.hasMoreElements();
            }

            @Override
            public String nextElement() {
                return (String) e.nextElement();
            }
        };

    }

}
