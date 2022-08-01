package au.gov.amsa.configuration.properties;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

final class ConfigurationUtil {

    /**
     * Constructor
     */
    private ConfigurationUtil() {
    }

    static Double getDouble(Configuration configuration, String key) {
        String value = (String) configuration.getProperty(key);
        if (value == null)
            return null;
        else
            return Double.parseDouble(value);
    }

    static Double getDoubleMandatory(Configuration configuration, String key) {
        return (Double) checkNotNull(key, getDouble(configuration, key));
    }

    private static Object checkNotNull(String key, Object object) {
        if (object == null)
            throw new RuntimeException("configuration item '" + key + "' must not be null");
        return object;
    }

    static Long getLong(Configuration configuration, String key) {
        String value = (String) configuration.getProperty(key);
        if (value == null)
            return null;
        else
            return Long.parseLong(value);
    }

    static Long getLongMandatory(Configuration configuration, String key) {
        return (Long) checkNotNull(key, getLong(configuration, key));
    }

    static Integer getInteger(Configuration configuration, String key) {
        String value = (String) configuration.getProperty(key);
        if (value == null)
            return null;
        else
            return Integer.parseInt(value);
    }

    static Integer getIntegerMandatory(Configuration configuration, String key) {
        return (Integer) checkNotNull(key, getInteger(configuration, key));
    }

    static String getString(Configuration configuration, String key) {
        return (String) configuration.getProperty(key);
    }

    static String getStringMandatory(Configuration configuration, String key) {
        return (String) checkNotNull(key, getString(configuration, key));
    }

    static boolean getBoolean(Configuration configuration, String key) {
        String s = getString(configuration, key);
        if (s == null)
            throw new RuntimeException("configuration item '" + key + "' must be set to true or false but is null");
        return "true".equalsIgnoreCase(s);
    }

    static Date getDateMandatory(Configuration configuration, String key) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = getStringMandatory(configuration, key);
        try {
            return df.parse(s);
        } catch (ParseException e) {
            throw new RuntimeException("date could not be parsed from configuration item " + key + "=" + s);
        }
    }

    static boolean getBooleanMandatory(Configuration configuration, String key) {
        String value = getString(configuration, key);
        return getBooleanMandatory(key, value);
    }

    // VisibleForTesting
    static boolean getBooleanMandatory(String key, String value) {
        if (value == null)
            throw new NullPointerException(
                    "configuration item '" + key + "' must be set to true or false but was null");
        if ("true".equalsIgnoreCase(value)) {
            return true;
        } else if ("false".equalsIgnoreCase(value)) {
            return false;
        } else {
            throw new IllegalArgumentException(
                    "configuration item '" + key + "' must be set to true or false but was '" + value + "'");
        }
    }
}
