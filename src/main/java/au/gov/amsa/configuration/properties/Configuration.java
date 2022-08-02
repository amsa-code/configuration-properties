package au.gov.amsa.configuration.properties;

import static au.gov.amsa.configuration.properties.Util.checkPresent;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TimeZone;

public interface Configuration {

    /**
     * Returns the string value of the configuration item corresponding to the key.
     * 
     * @param key should not be null
     * @return value corresponding to key if present
     */
    Optional<String> getString(String key);

    /**
     * Returns an enumeration of the keys present in the configuration.
     * 
     * @return keys
     */
    Enumeration<String> getKeys();

    default Set<String> getKeyset() {
        Enumeration<String> en = getKeys();
        Set<String> set = new HashSet<>();
        while (en.hasMoreElements()) {
            set.add(en.nextElement());
        }
        return set;
    }

    default String getStringMandatory(String key) {
        return checkPresent(getString(key), key);
    }

    default Optional<Double> getDouble(String key) {
        return getString(key).map(x -> Double.parseDouble(x));
    }

    default double getDoubleMandatory(String key) {
        return checkPresent(getDouble(key), key);
    }

    default Optional<Float> getFloat(String key) {
        return getString(key).map(x -> Float.parseFloat(x));
    }

    default float getFloatMandatory(String key) {
        return checkPresent(getFloat(key), key);
    }

    default Optional<Long> getLong(String key) {
        return getString(key).map(x -> Long.parseLong(x));
    }

    default long getLongMandatory(String key) {
        return checkPresent(getLong(key), key);
    }

    default Optional<Integer> getInteger(String key) {
        return getString(key).map(x -> Integer.parseInt(x));
    }

    default int getIntegerMandatory(String key) {
        return checkPresent(getInteger(key), key);
    }

    default boolean getBooleanMandatory(String key) {
        return checkPresent(getString(key).map(x -> Boolean.valueOf(x.trim().toLowerCase())), key);
    }

    default Date getDateMandatory(String key) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        String s = getStringMandatory(key);
        try {
            return df.parse(s);
        } catch (ParseException e) {
            throw new RuntimeException("date could not be parsed from configuration item " + key + "=" + s);
        }
    }

    default List<String> getStringListMandatory(String key, String delimiter) {
        String s = getStringMandatory(key);
        if (s.equals("")) {
            return Collections.emptyList();
        } else {
            return Arrays.asList(getStringMandatory(key).split(delimiter));
        }
    }

    default List<String> getStringListMandatory(String key, String delimiter, int minSize) {
        if (minSize <= 0) {
            throw new IllegalArgumentException("minSize must be > 0");
        }
        List<String> list = getStringListMandatory(key, delimiter);
        if (list.size() < minSize) {
            throw new IllegalArgumentException("list " + key + " must have at least " + minSize + " elements");
        }
        return list;
    }

    default List<String> getStringList(String key, String delimiter) {
        Optional<String> s = getString(key);
        if (s.isPresent() && s.get().equals("")) {
            return Collections.emptyList();
        } else {
            return s.map(x -> Arrays.asList(x.split(delimiter))).orElse(Collections.emptyList());
        }
    }

}
