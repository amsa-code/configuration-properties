package au.gov.amsa.configuration.properties;

import java.util.Enumeration;

public class EncryptedConfiguration implements Configuration {

    private static final Object DISABLED = new Object();
    private static final ThreadLocal<Object> enableDecryption = new ThreadLocal<>();

    private static final String PREFIX_ENCRYPTED = "encrypted:";
    private final Configuration configuration;
    private final Decrypter decrypter;

    public EncryptedConfiguration(Configuration configuration, Decrypter decrypter) {
        this.configuration = configuration;
        this.decrypter = decrypter;
    }

    @Override
    public Object getProperty(String context, String name) {
        try {
            Object value = configuration.getProperty(context, name);
            if (value instanceof String && String.valueOf(value).startsWith(PREFIX_ENCRYPTED)
                    && isDecryptionEnabled()) {
                return decrypter.decrypt(String.valueOf(value).substring(PREFIX_ENCRYPTED.length()));
            } else {
                return value;
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("could not get property for name=" + name, e);
        }
    }

    public static void disableDecryption() {
        enableDecryption.set(DISABLED);
    }

    public static void enableDecryption() {
        enableDecryption.remove();
    }

    private boolean isDecryptionEnabled() {
        boolean result = enableDecryption.get() != DISABLED;
        if (result) {
            // though the value in the ThreadLocal map is null
            // we still need to clean up the entry by calling remove
            // (we created an entry by calling .get())
            enableDecryption.remove();
        }
        return result;
    }

    @Override
    public Enumeration<String> getPropertyNames(String context) {
        return configuration.getPropertyNames(context);
    }

}
