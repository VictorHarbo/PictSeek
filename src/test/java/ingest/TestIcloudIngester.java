package ingest;

import PictSeek.ingest.IcloudIngester;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TestIcloudIngester {
    private static Logger log = LoggerFactory.getLogger(TestIcloudIngester.class);

    @Test
    public void testLogging(){
        log.info("This is a test message");
    }

    //@Test
    public void testIngester() throws IOException, InterruptedException {
        IcloudIngester.ingest();
    }
}
