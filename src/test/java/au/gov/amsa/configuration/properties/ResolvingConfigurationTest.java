package au.gov.amsa.configuration.properties;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Ignore;
import org.junit.Test;

import com.github.davidmoten.guavamini.Sets;
import com.github.davidmoten.security.PPK;

public final class ResolvingConfigurationTest {

    @Test
    public void test1() {
        String input = "name=fred";
        Configuration c = createConfiguration(input);
        assertEquals("fred", c.getStringMandatory("name"));
    }

    @Test
    public void test2() {
        String input = "name=${reference}\nreference=dave";
        Configuration c = createConfiguration(input);
        assertEquals("dave", c.getStringMandatory("name"));
    }

    @Test
    public void test3() {
        String input = "name=${reference}${reference}${label}\nreference=dave\nlabel=joy";
        Configuration c = createConfiguration(input);
        assertEquals("davedavejoy", c.getStringMandatory("name"));
    }

    @Test
    public void test4() {
        String input = "name=${reference}";
        Configuration c = createConfiguration(input);
        assertEquals("${reference}", c.getStringMandatory("name"));
    }

    @Test
    public void test5() {
        String input = "name=${reference}\nreference=${label}\nlabel=hello";
        Configuration c = createConfiguration(input);
        assertEquals("hello", c.getStringMandatory("name"));
    }

    @Test
    public void test6() {
        String input = "name=boo";
        Configuration c = createConfiguration(input);
        assertEquals("boo", c.getStringMandatory("name"));
    }

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

    @Test(expected = StackOverflowError.class)
    @Ignore
    public void testCircularReference() {
        String input = "name=${reference}\nreference=${label}\nlabel=${reference}";
        Configuration c = createConfiguration(input);
        assertEquals("hello", c.getStringMandatory("name"));
    }

    private Configuration createConfiguration(String input) {
        return new ResolvingConfiguration(
                new AutoClosingInputStreamConfiguration(new ByteArrayInputStream(input.getBytes())));
    }

    private static Configuration createConfigurationWithDecrypter(String input) {
        Configuration config = new AutoClosingInputStreamConfiguration(new ByteArrayInputStream(input.getBytes()));
        return new ResolvingConfiguration(new EncryptedConfiguration(config, new Decrypter(new PrivateKeyProvider() {

            @Override
            public InputStream getInputStream() {
                return ResolvingConfigurationTest.class.getResourceAsStream("/test-private.der");
            }
        })));
    }

}
