import PictSeek.metadata.ExifHandler;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TestMetadataWorkflows {

    @Test
    public void test() throws IOException {
        System.out.println("PNG orignal");
        ExifHandler.metadataExtraction("/Users/vhol/Pictures/testTiffs/IMG_0749.tiff");

        System.out.println("HEIC original");
        ExifHandler.metadataExtraction("/Users/vhol/Pictures/testTiffs/IMG_0748.tiff");
    }

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
