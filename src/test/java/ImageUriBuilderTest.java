import PictSeek.metadata.ImageUrlBuilder;
import PictSeek.config.ServiceConfig;
import org.apache.http.client.utils.URIBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ImageUriBuilderTest {

    @BeforeAll
    public static void setup() throws IOException {
        ServiceConfig.initialize("conf/PictSeek-behaviour.yaml");
    }

    @Test
    public void testUriConstruction() throws URISyntaxException {
        URIBuilder builder = ImageUrlBuilder.createFifOnlyUri("src/test/resources/testImage.tiff");
        assertEquals("IIPImage.com/imageserver?FIF=%2FtestImage.tiff", builder.build().toString());
    }
}
