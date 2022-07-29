package au.gov.amsa.configuration.properties;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.github.davidmoten.security.PPK;

public final class ResolvingConfigurationTest {

    @Test
    public void test1() {
        String input = "name=fred";
        Configuration c = createConfiguration(input);
        Assert.assertEquals("fred", ConfigurationUtil.getString(c, "name"));
    }

    @Test
    public void test2() {
        String input = "name=${reference}\nreference=dave";
        Configuration c = createConfiguration(input);
        Assert.assertEquals("dave", ConfigurationUtil.getString(c, "name"));
    }

    @Test
    public void test3() {
        String input = "name=${reference}${reference}${label}\nreference=dave\nlabel=joy";
        Configuration c = createConfiguration(input);
        Assert.assertEquals("davedavejoy", ConfigurationUtil.getString(c, "name"));
    }

    @Test
    public void test4() {
        String input = "name=${reference}";
        Configuration c = createConfiguration(input);
        Assert.assertEquals("${reference}", ConfigurationUtil.getString(c, "name"));
    }

    @Test
    public void test5() {
        String input = "name=${reference}\nreference=${label}\nlabel=hello";
        Configuration c = createConfiguration(input);
        Assert.assertEquals("hello", ConfigurationUtil.getString(c, "name"));
    }

    @Test
    public void test6() {
        String input = "name=boo";
        Configuration c = createConfiguration(input);
        Assert.assertEquals("boo", ConfigurationUtil.getString(c, "name"));
    }

    @Test
    public void testResolvingWithEncryption() {
        String enc = PPK.publicKey("/test-public.der").encryptAsBase64("alpha");
        String input = "aussar.url=${aussar.username}/${aussar.password}@${aussar.tns.name}\n"
                + "aussar.username=admin\n" + "aussar.password=encrypted:" + enc + "\n"
                + "aussar.tns.name=bingo";
        Configuration c = createConfigurationWithDecrypter(input);
        assertEquals("admin/alpha@bingo", ConfigurationUtil.getString(c, "aussar.url"));
    }

    @Test(expected = StackOverflowError.class)
    @Ignore
    public void testCircularReference() {
        String input = "name=${reference}\nreference=${label}\nlabel=${reference}";
        Configuration c = createConfiguration(input);
        Assert.assertEquals("hello", ConfigurationUtil.getString(c, "name"));
    }

    private Configuration createConfiguration(String input) {
        return new ResolvingConfiguration(
                new AutoClosingInputStreamConfiguration(new ByteArrayInputStream(input.getBytes())));
    }

    private static Configuration createConfigurationWithDecrypter(String input) {
        Configuration config = new AutoClosingInputStreamConfiguration(
                new ByteArrayInputStream(input.getBytes()));
        return new ResolvingConfiguration(
                new EncryptedConfiguration(config, new Decrypter(new PrivateKeyProvider() {

                    @Override
                    public InputStream getInputStream() {
                        return ResolvingConfigurationTest.class
                                .getResourceAsStream("/test-private.der");
                    }
                })));
    }

}
