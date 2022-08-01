package au.gov.amsa.configuration.properties;

import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Optional;

public class ConfigurationWriter {

    public final void write(Configuration configuration, PrintStream out) {
        Enumeration<String> enumeration = configuration.getKeys();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            Optional<String> value = configuration.getString(key);
            if (value.isPresent()) {
                out.println(key + "=" + value.get());
            }
        }
    }

}
