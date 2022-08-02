package au.gov.amsa.configuration.properties;

import java.util.Enumeration;
import java.util.Optional;

public final class EncryptedConfiguration implements Configuration {

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
            if (value.isPresent() && value.get().startsWith(PREFIX_ENCRYPTED)) {
                return Optional.of(decrypter.decrypt(value.get().substring(PREFIX_ENCRYPTED.length())));
            } else {
                return value;
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("could not decrypt property for name=" + name, e);
        }
    }

    @Override
    public Enumeration<String> getKeys() {
        return configuration.getKeys();
    }

}
