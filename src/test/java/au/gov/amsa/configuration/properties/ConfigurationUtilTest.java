package au.gov.amsa.configuration.properties;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public final class ConfigurationUtilTest {
    
    @Test
    public void testBooleanMandatoryTrue() {
        assertTrue(ConfigurationUtil.getBooleanMandatory("x", "trUe"));
    }
    
    @Test
    public void testBooleanMandatoryFalse() {
        assertFalse(ConfigurationUtil.getBooleanMandatory("x", "falSE"));
    }

    @Test(expected = NullPointerException.class)
    public void testBooleanMandatoryNull() {
        assertFalse(ConfigurationUtil.getBooleanMandatory("x", null));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testBooleanMandatoryHasTrailingSpace() {
        assertFalse(ConfigurationUtil.getBooleanMandatory("x", "true      "));
    }
}
