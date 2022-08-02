package au.gov.amsa.configuration.properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

import org.junit.Test;

import com.github.davidmoten.guavamini.Sets;
import com.github.davidmoten.security.PPK;

public class EncryptedConfigurationTest {

    @Test
    public void testResolvingWithEncryption() {
        String enc = PPK.publicKey("/test-public.der").encryptAsBase64("alpha");
        String input = "aussar.url=${aussar.username}/${aussar.password}@${aussar.tns.name}\n"
                + "aussar.username=admin\n" + "aussar.password=encrypted:" + enc + "\n" + "aussar.tns.name=bingo";
        Configuration c = createConfigurationWithDecrypter(input);
        assertEquals("admin/alpha@bingo", c.getStringMandatory("aussar.url"));
        assertEquals(Sets.newHashSet("aussar.username", "aussar.url", "aussar.tns.name", "aussar.password"),
                c.getKeyset());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotEncryptedBadFormat() {
        Configuration base = ConfigurationFromMap.add("hello", "encrypted:there").build();
        Configuration c = createConfigurationWithDecrypter(base);
        c.getString("hello");
    }

    @Test
    public void testPrivateKeyFromConfiguration() throws IOException {
        Configuration c = ConfigurationFromMap.add(PrivateKeyProviderFromConfiguration.ENCRYPTION_PRIVATE_KEY_FILE,
                "src/test/resources/test-private.der").build();
        try (InputStream in = new PrivateKeyProviderFromConfiguration(c).getInputStream()) {
            assertNotNull(in);
        }
    }

    @Test(expected = UncheckedIOException.class)
    public void testPrivateKeyFromConfigurationNotPresent() throws IOException {
        Configuration c = ConfigurationFromMap
                .add(PrivateKeyProviderFromConfiguration.ENCRYPTION_PRIVATE_KEY_FILE, "not.present") //
                .build();
        new PrivateKeyProviderFromConfiguration(c).getInputStream();
    }

    private static Configuration createConfigurationWithDecrypter(String input) {
        Configuration config = new AutoClosingInputStreamConfiguration(new ByteArrayInputStream(input.getBytes()));
        return createConfigurationWithDecrypter(config);
    }

    private static ResolvingConfiguration createConfigurationWithDecrypter(Configuration config) {
        return new ResolvingConfiguration(new EncryptedConfiguration(config,
                new Decrypter(() -> ResolvingConfigurationTest.class.getResourceAsStream("/test-private.der"))));
    }

}
