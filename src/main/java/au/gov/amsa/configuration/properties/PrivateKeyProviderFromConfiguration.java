package au.gov.amsa.configuration.properties;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.function.Supplier;

public final class PrivateKeyProviderFromConfiguration implements PrivateKeyProvider {

    public static final String ENCRYPTION_PRIVATE_KEY_FILE = "encryption.private.key.file";
    private final Supplier<Configuration> provider;

    public PrivateKeyProviderFromConfiguration(Supplier<Configuration> provider) {
        this.provider = provider;
    }

    public PrivateKeyProviderFromConfiguration(final Configuration configuration) {
        this(new Supplier<Configuration>() {
            @Override
            public Configuration get() {
                return configuration;
            }
        });
    }

    @Override
    public InputStream getInputStream() {
        File file = new File(provider.get().getStringMandatory(ENCRYPTION_PRIVATE_KEY_FILE));
        try {
            return new BufferedInputStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new UncheckedIOException(e);
        }
    }

}
