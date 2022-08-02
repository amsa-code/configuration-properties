package au.gov.amsa.configuration.properties;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;

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

    private static Configuration createConfigurationWithDecrypter(String input) {
        Configuration config = new AutoClosingInputStreamConfiguration(new ByteArrayInputStream(input.getBytes()));
        return new ResolvingConfiguration(new EncryptedConfiguration(config,
                new Decrypter(() -> ResolvingConfigurationTest.class.getResourceAsStream("/test-private.der"))));
    }

}
