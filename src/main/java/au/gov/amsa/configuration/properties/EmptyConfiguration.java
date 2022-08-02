package au.gov.amsa.configuration.properties;

import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Optional;

public final class EmptyConfiguration implements Configuration {

    @Override
    public Optional<String> getString(String name) {
        return Optional.empty();
    }

    @Override
    public Enumeration<String> getKeys() {
        return new Enumeration<String>() {

            @Override
            public boolean hasMoreElements() {
                return false;
            }

            @Override
            public String nextElement() {
                throw new NoSuchElementException();
            }
        };
    }

}
