package PictSeek.metadata;

import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class ExifHandler {
    private static Logger log = LoggerFactory.getLogger(ExifHandler.class);

    public static void metadataExtraction(String imagePath) throws IOException {

        ImageMetadata metadata = getImageMetadata(imagePath);

        log.info(metadata.toString());
    }
    public static int getImageWidth(String imagePath) throws IOException {
        return getFieldValueAsInt(imagePath, "ImageWidth" );
    }

    public static int getImageLength(String imagePath) throws IOException {
        return getFieldValueAsInt(imagePath, "ImageLength");
    }

    private static int getFieldValueAsInt(String imagePath, String fieldName) throws IOException {
        ImageMetadata metadata = getImageMetadata(imagePath);

        if (metadata instanceof TiffImageMetadata tiffMetadata){
            for (TiffField field : tiffMetadata.getAllFields()){
                if (field.getTagName().equals(fieldName)){
                    return field.getIntValue();
                }
            }
        }
        // If we ever get here throw an Exception.
        throw new IOException("Integer value could not be extracted for field: '" + fieldName +"' from image at: " + imagePath);
    }


    private static ImageMetadata getImageMetadata(String imagePath) throws IOException {
        File image = new File(imagePath);
        return Imaging.getMetadata(image);
    }

}
