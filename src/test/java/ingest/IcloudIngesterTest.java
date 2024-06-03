package ingest;

import PictSeek.ingest.IcloudIngester;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class IcloudIngesterTest {
    private static Logger log = LoggerFactory.getLogger(IcloudIngesterTest.class);

    @Test
    public void testLogging(){
        log.info("This is a test message");
    }

    //@Test
    public void testIngester() throws IOException, InterruptedException {
        IcloudIngester.ingest();
    }
}
