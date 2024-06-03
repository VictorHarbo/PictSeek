package PictSeek;

import PictSeek.metadata.ExifHandler;
import PictSeek.metadata.SolrDocument;

import java.io.IOException;

public class Facade {

    public static SolrDocument createSolrDocumentForImage(String imagePath) throws IOException {
        String id = SolrDocument.createId();
        Integer imageLength = ExifHandler.getImageLength(imagePath);
        Integer imageWidth = ExifHandler.getImageWidth(imagePath);


        return new SolrDocument(id, imageWidth, imageLength);
    }
}
