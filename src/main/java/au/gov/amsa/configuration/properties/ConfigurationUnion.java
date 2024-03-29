package au.gov.amsa.configuration.properties;

import java.util.Enumeration;
import java.util.Optional;

/**
 * Joins two configurations to provide one effective configuration file. The
 * first of the two configurations overrides the second.
 * 
 */
public final class ConfigurationUnion implements Configuration {

    private final Configuration c1;
    private final Configuration c2;

    /**
     * The first of the two configurations overrides the second.
     * 
     * @param c1 first configuration 
     * @param c2 second configuration
     */
    public ConfigurationUnion(Configuration c1, Configuration c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

    @Override
    public Optional<String> getString(String key) {
        Optional<String> a = c1.getString(key);
        if (a.isPresent()) {
            return a;
        } else {
            return c2.getString(key);
        }
    }

    @Override
    public Enumeration<String> getKeys() {
        final Enumeration<String> e1 = c1.getKeys();
        final Enumeration<String> e2 = c2.getKeys();
        return enumeration(e1, e2);
    }

    private static Enumeration<String> enumeration(final Enumeration<String> e1, final Enumeration<String> e2) {
        return new Enumeration<String>() {

            @Override
            public boolean hasMoreElements() {
                return e1.hasMoreElements() || e2.hasMoreElements();
            }

            @Override
            public String nextElement() {
                if (e1.hasMoreElements())
                    return e1.nextElement();
                else
                    return e2.nextElement();
            }
        };
    }
}
