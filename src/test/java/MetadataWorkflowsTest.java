import PictSeek.metadata.ExifHandler;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MetadataWorkflowsTest {

    String testImage = "src/test/resources/testImage.tiff";

    @Test
    public void testGetWidth() throws IOException {
        int width = ExifHandler.getImageWidth(testImage);
        assertEquals(791, width);
    }

    @Test
    public void testGetHeight() throws IOException {
        int length = ExifHandler.getImageLength(testImage);
        assertEquals(660, length);
    }

    @Test
    public void testContent() throws IOException {
        ExifHandler.metadataExtraction(testImage);
    }
}
