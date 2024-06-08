package ingest;

import PictSeek.ingest.ImageIngester;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class imageIngesterTest {
    private static Logger log = LoggerFactory.getLogger(imageIngesterTest.class);

    @Test
    public void testLogging(){
        log.info("This is a test message");
    }

    //@Test
    public void testIngester() throws IOException {
        ImageIngester.ingest();
    }
}
