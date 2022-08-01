package au.gov.amsa.configuration.properties;

import java.util.Enumeration;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResolvingConfiguration implements Configuration {

    private final Configuration configuration;

    public ResolvingConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    private Optional<String> resolve(Optional<String> sourcestring, Configuration configuration) {
        if (!sourcestring.isPresent()) {
            return sourcestring;
        }
        Pattern re = Pattern.compile("\\$\\{(.+?)\\}");
        Matcher m = re.matcher(sourcestring.get());
        StringBuffer result = new StringBuffer();
        while (m.find()) {
            String variable = m.group(1);
            Optional<String> resolved = resolve(configuration.getString(variable), configuration);
            if (resolved.isPresent())
                try {
                    m.appendReplacement(result, resolved.get());
                } catch (RuntimeException e) {
                    throw e;
                }
        }
        m.appendTail(result);
        if (result.length() == 0) {
            return Optional.empty();
        }
        return Optional.of(result.toString());
    }

    @Override
    public final Optional<String> getString(String name) {
        return resolve(configuration.getString(name), configuration);
    }

    @Override
    public final Enumeration<String> getKeys() {
        return configuration.getKeys();
    }

}
