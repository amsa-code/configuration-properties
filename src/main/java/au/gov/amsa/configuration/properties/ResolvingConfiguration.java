package au.gov.amsa.configuration.properties;

import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResolvingConfiguration implements Configuration {

    private final Configuration configuration;

    public ResolvingConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    private String resolve(String sourcestring, Configuration configuration) {
        if (sourcestring == null)
            return null;
        Pattern re = Pattern.compile("\\$\\{(.+?)\\}");
        Matcher m = re.matcher(sourcestring);
        StringBuffer result = new StringBuffer();
        while (m.find()) {
            String variable = m.group(1);
            String resolved = resolve((String) configuration.getProperty(variable), configuration);
            if (resolved != null)
                try {
                    m.appendReplacement(result, resolved);
                } catch (RuntimeException e) {
                    //TODO no sysout
                    System.out.println(
                            "called appendReplacement(sb,replacement) with sb=" + result + ",replacement=" + resolved);
                    System.out.println(e.getMessage());
                    throw e;
                }
        }
        m.appendTail(result);
        return result.toString();
    }

    @Override
    public final String getProperty(String name) {
        String result = resolve((String) configuration.getProperty(name), configuration);
        return result;
    }

    @Override
    public final Enumeration<String> getPropertyNames() {
        return configuration.getPropertyNames();
    }

}
