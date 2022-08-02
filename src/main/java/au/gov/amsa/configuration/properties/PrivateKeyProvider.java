package au.gov.amsa.configuration.properties;

import java.io.InputStream;

@FunctionalInterface
public interface PrivateKeyProvider {
    InputStream getInputStream();
}
