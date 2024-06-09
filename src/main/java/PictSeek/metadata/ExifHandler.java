package PictSeek.metadata;

import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Class handling image metadata from EXIF format.
 */
public class ExifHandler {
    private static Logger log = LoggerFactory.getLogger(ExifHandler.class);

    /**
     * Simple method which logs EXIF metadata for an image.
     * @param imagePath to get EXIF from.
     */
    public static void metadataExtraction(String imagePath) throws IOException {

        ImageMetadata metadata = getImageMetadata(imagePath);

        log.info(metadata.toString());
    }


    /**
     * Get the width from the EXIF metadata.
     * @param imagePath to image.
     * @return the width of the image as an int.
     */
    public static int getImageWidth(String imagePath) throws IOException {
        return getFieldValueAsInt(imagePath, "ImageWidth" );
    }

    /**
     * Get the length from the EXIF metadata.
     * @param imagePath to image.
     * @return the length of the image as an int.
     */
    public static int getImageLength(String imagePath) throws IOException {
        return getFieldValueAsInt(imagePath, "ImageLength");
    }

    /**
     * Get the value from a EXIF field as an int.
     * @param imagePath to image.
     * @param fieldName to extract as an int.
     * @return the value from fieldName as an int.
     */
    private static int getFieldValueAsInt(String imagePath, String fieldName) throws IOException {
        ImageMetadata metadata = getImageMetadata(imagePath);

        if (metadata instanceof TiffImageMetadata){
            TiffImageMetadata tiffMetadata = (TiffImageMetadata) metadata;
            for (TiffField field : tiffMetadata.getAllFields()){
                if (field.getTagName().equals(fieldName)){
                    return field.getIntValue();
                }
            }
        }
        // If we ever get here throw an Exception.
        throw new IOException("Integer value could not be extracted for field: '" + fieldName +"' from image at: " + imagePath);
    }


    /**
     * Get ImageMetadata from an image.
     * @param imagePath to image.
     * @return ImageMetadata for the input image.
     */
    private static ImageMetadata getImageMetadata(String imagePath) throws IOException {
        File image = new File(imagePath);
        return Imaging.getMetadata(image);
    }

}
