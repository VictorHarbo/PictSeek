import PictSeek.metadata.ExifHandler;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MetadataWorkflowsTest {

    @Test
    public void testGetWidth() throws IOException {
        int width = ExifHandler.getImageWidth("src/test/resources/testImage.tiff");
        assertEquals(791, width);
    }

    @Test
    public void testGetHeight() throws IOException {
        int length = ExifHandler.getImageLength("src/test/resources/testImage.tiff");
        assertEquals(660, length);
    }
}
