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

    // TODO check for circular references so don't get StackOverflowError
    // we don't want the scenario where one configuration change on a single
    // webapp breaks the whole container. Never happened but probably worth
    // protecting against.
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
                appendReplacement(m, result, resolved);
        }
        m.appendTail(result);
        if (result.length() == 0) {
            return Optional.empty();
        }
        return Optional.of(result.toString());
    }

    // VisibleForTesting
    static void appendReplacement(Matcher m, StringBuffer result, Optional<String> resolved) {
        try {
            m.appendReplacement(result, resolved.get());
        } catch (RuntimeException e) {
            throw e;
        }
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
