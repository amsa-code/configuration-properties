package au.gov.amsa.configuration.properties;

import java.util.Optional;

final class Util {
    
    private Util() {
        // prevent instantiation
    }

    static <T> T checkPresent(Optional<? extends T> x, String key) throws ValueNotFoundException {
        return x.<ValueNotFoundException>orElseThrow(() -> {
           throw new ValueNotFoundException(key); 
        });
    }
}
