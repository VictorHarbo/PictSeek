package PictSeek;

import PictSeek.metadata.ExifHandler;
import PictSeek.metadata.MetadataDocument;

import java.io.IOException;

public class Facade {
    public static MetadataDocument createSolrDocumentForImage(String imagePath)  {
        String id = MetadataDocument.createId();
        int imageLength;
        int imageWidth;
        try {
            imageLength = ExifHandler.getImageLength(imagePath);
            imageWidth = ExifHandler.getImageWidth(imagePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new MetadataDocument(id, imageWidth, imageLength );
    }
}
