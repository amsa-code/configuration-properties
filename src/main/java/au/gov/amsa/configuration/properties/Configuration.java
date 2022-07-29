package au.gov.amsa.configuration.properties;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;

/**
 * General property access
 * 
 * @author dxm
 * 
 */
public interface Configuration {

    String getProperty(String name);

    Enumeration<String> getPropertyNames();

    default Optional<Double> getDouble(String key) {
        return Optional.ofNullable(ConfigurationUtil.getDouble(this, key));
    }

    default double getDoubleMandatory(String key) {
        return ConfigurationUtil.getDoubleMandatory(this, key);
    }

    default Optional<Long> getLong(String key) {
        return Optional.ofNullable(ConfigurationUtil.getLong(this, key));
    }

    default long getLongMandatory(String key) {
        return ConfigurationUtil.getLongMandatory(this, key);
    }

    default Optional<Integer> getInteger(String key) {
        return Optional.ofNullable(ConfigurationUtil.getInteger(this, key));
    }

    default int getIntegerMandatory(String key) {
        return ConfigurationUtil.getIntegerMandatory(this, key);
    }

    default Optional<String> getString(String key) {
        return Optional.ofNullable(ConfigurationUtil.getString(this, key));
    }
    
    default Optional<String> getStringEmptyIfBlank(String key) {
        String s = ConfigurationUtil.getString(this, key);
        if (s != null && s.trim().isEmpty()) {
            s = null;
        }
        return Optional.ofNullable(s);
    }

    default String getStringMandatory(String key) {
        return ConfigurationUtil.getStringMandatory(this, key);
    }

    default boolean getBooleanMandatory(String key) {
        return ConfigurationUtil.getBooleanMandatory(this, key);
    }

    default Date getDateMandatory(String key) {
        return ConfigurationUtil.getDateMandatory(this, key);
    }

    default List<String> getStringListMandatory(String key, String delimiter) {
        return Arrays.asList(getStringMandatory(key).split(delimiter));
    }

    default List<String> getStringListMandatory(String key, String delimiter, int minSize) {
        if (minSize <=0) {
            throw new IllegalArgumentException("minSize must be > 0");
        }
        List<String> list = Arrays.asList(getStringMandatory(key).split(delimiter));
        if (list.size() < minSize) {
            throw new IllegalArgumentException("list " + key + " must have at least " + minSize + " elements");
        }
        return list;
    }

    default List<String> getStringList(String key, String delimiter) {
        return getString(key).map(x -> Arrays.asList(x.split(delimiter))).orElse(Collections.emptyList());
    }

    default float getFloatMandatory(String key) {
        return ConfigurationUtil.getDoubleMandatory(this, key).floatValue();
    }

}
