package au.gov.amsa.configuration.properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import org.junit.Test;

public class ConfigurationTest {

    private static final Configuration c = ConfigurationFromMap //
            .add("thing", "cup") //
            .add("thing.integer", "123") //
            .add("thing.integer.bad", "abc") //
            .add("thing.double", "123456.7890123") //
            .add("thing.double.bad", "abc") //
            .add("thing.boolean", "false") //
            .add("thing.boolean.uppercase", "FALSE") //
            .add("thing.boolean.bad", "abc") //
            .add("thing.list", "a,b,c") //
            .add("thing.list.empty", "") //
            .add("thing.date", "2022-03-01 15:45:02") //
            .add("thing.date.bad", "abc") //
            .build();

    @Test
    public void testString() {
        assertEquals(Optional.of("cup"), c.getString("thing"));
    }

    @Test
    public void testStringMandatory() {
        assertEquals("cup", c.getStringMandatory("thing"));
    }

    @Test(expected = ValueNotFoundException.class)
    public void testStringMandatoryNotPresent() {
        c.getStringMandatory("not.present");
    }

    @Test
    public void testInteger() {
        assertEquals(Optional.of(123), c.getInteger("thing.integer"));
    }

    @Test
    public void testIntegerMandatory() {
        assertEquals(123, c.getIntegerMandatory("thing.integer"));
    }

    @Test(expected = ValueNotFoundException.class)
    public void testIntegerMandatoryNotPresent() {
        c.getIntegerMandatory("not.present");
    }

    @Test(expected = NumberFormatException.class)
    public void testIntegerMandatoryWrongFormat() {
        c.getInteger("thing.integer.bad");
    }

    @Test
    public void testDouble() {
        assertEquals(123456.7890123, c.getDouble("thing.double").get(), 0.00001);
    }

    @Test
    public void testDoubleMandatory() {
        assertEquals(123456.7890123, c.getDoubleMandatory("thing.double"), 0.00001);
    }

    @Test(expected = ValueNotFoundException.class)
    public void testDoubleMandatoryNotPresent() {
        c.getDoubleMandatory("not.present");
    }

    @Test(expected = NumberFormatException.class)
    public void testDoubleMandatoryWrongFormat() {
        c.getDouble("thing.double.bad");
    }

    @Test
    public void testLong() {
        assertEquals(Optional.of(123L), c.getLong("thing.integer"));
    }

    @Test
    public void testLongMandatory() {
        assertEquals(123L, c.getLongMandatory("thing.integer"));
    }

    @Test(expected = ValueNotFoundException.class)
    public void testLongMandatoryNotPresent() {
        c.getLongMandatory("not.present");
    }

    @Test(expected = NumberFormatException.class)
    public void testLongMandatoryWrongFormat() {
        c.getLong("thing.integer.bad");
    }

    @Test
    public void testFloat() {
        assertEquals(123456.789f, c.getFloat("thing.double").get(), 0.001);
    }

    @Test
    public void testFloatMandatory() {
        assertEquals(123456.789, c.getFloatMandatory("thing.double"), 0.001);
    }

    @Test(expected = ValueNotFoundException.class)
    public void testFloatMandatoryNotPresent() {
        c.getFloatMandatory("not.present");
    }

    @Test(expected = NumberFormatException.class)
    public void testFloatMandatoryWrongFormat() {
        c.getFloat("thing.double.bad");
    }

    @Test
    public void testBoolean() {
        assertFalse(c.getBooleanMandatory("thing.boolean"));
    }

    @Test
    public void testBooleanUppercase() {
        assertFalse(c.getBooleanMandatory("thing.boolean.uppercase"));
    }

    @Test
    public void testStringList() {
        assertEquals(Arrays.asList("a", "b", "c"), c.getStringList("thing.list", ","));
    }

    @Test
    public void testStringListEmpty() {
        assertTrue(c.getStringList("thing.list.empty", ",").isEmpty());
    }

    @Test
    public void testStringListNotPresent() {
        assertTrue(c.getStringList("thing.list.not.present", ",").isEmpty());
    }

    @Test
    public void testStringListMandatory() {
        assertEquals(Arrays.asList("a", "b", "c"), c.getStringListMandatory("thing.list", ","));
    }

    @Test
    public void testStringListMandatoryEmpty() {
        assertTrue(c.getStringListMandatory("thing.list.empty", ",").isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStringListMandatoryFailsMinSize() {
        c.getStringListMandatory("thing.list.empty", ",", 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStringListMandatoryFailsMinSizeInvalid() {
        c.getStringListMandatory("thing.list.empty", ",", -1);
    }

    @Test
    public void testStringListMandatoryPassesMinSize() {
        c.getStringListMandatory("thing.list", ",", 1);
    }

    @Test
    public void testDate() {
        Date d = c.getDateMandatory("thing.date");
        assertEquals(1646149502000L, d.getTime());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDateBad() {
        c.getDateMandatory("thing.date.bad");
    }

    @Test
    public void testKey() {
        ValueNotFoundException e = new ValueNotFoundException("abc");
        assertEquals("abc", e.key());
    }

    @Test
    public void testWrite() {
        Configuration c = ConfigurationFromMap //
                .add("hello", "there") //
                .add("thing", "") //
                .build();
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (PrintStream out = new PrintStream(bytes)) {
            c.write(out);
        }
        assertEquals("thing=\nhello=there\n", new String(bytes.toByteArray(), StandardCharsets.UTF_8));
    }

}
