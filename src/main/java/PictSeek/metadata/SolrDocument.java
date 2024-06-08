package PictSeek.metadata;

import java.util.UUID;

/**
 * This class represents the JSON solr documents indexed into solr.
 */
public class SolrDocument {


    private String id;
    private Integer imageWidth;
    private Integer imageLength;

    public SolrDocument(String id, Integer imageWidth, Integer imageLength) {
        this.id = id;
        this.imageWidth = imageWidth;
        this.imageLength = imageLength;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(Integer imageWidth) {
        this.imageWidth = imageWidth;
    }

    public Integer getImageLength() {
        return imageLength;
    }

    public void setImageLength(Integer imageLength) {
        this.imageLength = imageLength;
    }

    public static String createId(){
        UUID id = UUID.randomUUID();
        return id.toString();
    }
}
