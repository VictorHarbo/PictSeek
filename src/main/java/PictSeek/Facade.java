package PictSeek;

import PictSeek.metadata.ExifHandler;
import PictSeek.metadata.MetadataDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;

public class Facade {
    private static Logger log = LoggerFactory.getLogger(Facade.class);

    public static MetadataDocument createSolrDocumentForImage(String imagePath)  {
        MetadataDocument doc = new MetadataDocument();

        doc.setId(MetadataDocument.createId());
        try{
            doc.setImageLength(ExifHandler.getImageLength(imagePath));
            doc.setImageWidth(ExifHandler.getImageWidth(imagePath));
        } catch (IOException e) {
            log.warn("An error occurred when loading EXIF data from image located at: '{}'", imagePath);
            throw new RuntimeException(e);
        }

        try {
            doc.setUrlOriginal(ImageUrlBuilder.createUrlOriginal(imagePath));
            doc.setUrlLargeSize(ImageUrlBuilder.createUrlLarge(imagePath));
            doc.setUrlMediumSize(ImageUrlBuilder.createUrlMedium(imagePath));
            doc.setUrlSmallSize(ImageUrlBuilder.createUrlSmall(imagePath));
            doc.setUrlThumbnail(ImageUrlBuilder.createUrlThumbnail(imagePath));
        } catch (URISyntaxException e) {
            log.warn("Encountered a problem when creating URLs for image at path: '{}'", imagePath);
            throw new RuntimeException(e);
        }

        // TODO: Add urls for each size to doc.
        // TODO: Add AI generated metadata for each image.

        return doc;
    }
}
