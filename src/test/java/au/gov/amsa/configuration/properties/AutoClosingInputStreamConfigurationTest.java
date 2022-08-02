package au.gov.amsa.configuration.properties;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

import org.junit.Test;

public class AutoClosingInputStreamConfigurationTest {

    @Test(expected = UncheckedIOException.class)
    public void testInputStreamThrowsOnRead() {
        new AutoClosingInputStreamConfiguration(new InputStream() {

            @Override
            public int read() throws IOException {
                throw new IOException("boo");
            }
        });
    }

    @Test(expected = UncheckedIOException.class)
    public void testInputStreamThrowsOnClose() {
        new AutoClosingInputStreamConfiguration(new InputStream() {

            @Override
            public int read() throws IOException {
                return -1;
            }

            @Override
            public void close() throws IOException {
                throw new IOException("boo");
            }
        });
    }

}
