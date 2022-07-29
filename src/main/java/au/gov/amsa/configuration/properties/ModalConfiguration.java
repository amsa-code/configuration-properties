package au.gov.amsa.configuration.properties;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Enumeration;

import au.gov.amsa.util.security.Decrypter;
import au.gov.amsa.util.security.PrivateKeyProviderFromConfiguration;

public class ModalConfiguration implements Configuration {

    private final Configuration configuration;

    public ModalConfiguration() throws FileNotFoundException {
        this(getMode(), ModalConfiguration.class);
    }

    public ModalConfiguration(Class<?> cls) throws FileNotFoundException {
        this(getMode(), cls);
    }

    public ModalConfiguration(String mode, Class<?> cls) throws FileNotFoundException {
        if ("classpath".equals(mode))
            configuration = new ClasspathConfiguration("/modal-configuration-embedded.properties",
                    cls);
        else {
            final Configuration base = new FileSystemConfiguration(getModalFilename(mode));
            // decrypt
            configuration = new ResolvingConfiguration(new EncryptedConfiguration(base,
                    new Decrypter(new PrivateKeyProviderFromConfiguration(
                            new ResolvingConfiguration(base)))));
        }
    }

    private static String getModalFilename(String mode) {
        String filename = System.getProperty("modal.configuration");
        if (filename == null) {
            filename = System.getProperty("apps.dir");
            if (filename != null)
                filename = filename + File.separatorChar + "configuration" + File.separatorChar
                        + "configuration.properties";
            else
                filename = "/aus" + mode + "/configuration/configuration.properties";
        }
        return filename;
    }

    private static String getMode() {
        String mode = System.getProperty("mode");
        if (mode == null) {
            throw new ModeNotSetException("System property 'mode' should not be null");
        }
        return mode;
    }

    @Override
    public Object getProperty(String context, String name) {
        return configuration.getProperty(context, name);
    }

    @Override
    public Enumeration<String> getPropertyNames(String context) {
        return configuration.getPropertyNames(context);
    }
}
