package au.gov.amsa.configuration.properties;

import java.util.Enumeration;
import java.util.Optional;

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
    public Optional<String> getString(String name) {
        try {
            Optional<String> value = configuration.getString(name);
            if (value.isPresent() && value.get().startsWith(PREFIX_ENCRYPTED) && isDecryptionEnabled()) {
                return Optional.of(decrypter.decrypt(value.get().substring(PREFIX_ENCRYPTED.length())));
            } else {
                return value;
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("could not decrypt property for name=" + name, e);
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
    public Enumeration<String> getKeys() {
        return configuration.getKeys();
    }

}
