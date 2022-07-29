package au.gov.amsa.configuration.properties;

import java.io.InputStream;

public interface PrivateKeyProvider {
    InputStream getInputStream();
}
