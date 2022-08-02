package au.gov.amsa.configuration.properties;

import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Test;

import com.github.davidmoten.junit.Asserts;

public class UtilTest {
    
    @Test
    public void isUtilityClass() {
        Asserts.assertIsUtilityClass(Util.class);
    }
    
    @Test
    public void testWriteBlankProperty() {
        AtomicBoolean written = new AtomicBoolean();
        OutputStream os = new OutputStream() {

            @Override
            public void write(int b) throws IOException {
                written.getAndSet(true);
            }
        };
        PrintStream out = new PrintStream(os);
        Util.write(out, "thing", Optional.empty());
        assertFalse(written.get());
        out.close();
    }

}
