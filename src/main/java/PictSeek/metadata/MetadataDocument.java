package PictSeek.metadata;

import java.util.UUID;

/**
 * This class represents the JSON solr documents indexed into solr.
 */
public class MetadataDocument {


    private String id;
    private Integer imageWidth;
    private Integer imageLength;

    private String description = "";
    private String urlThumbnail = "";
    private String urlOriginal = "";
    private String urlLargeSize = "";
    private String urlMediumSize = "";
    private String urlSmallSize = "";

    public MetadataDocument(){
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

    public String getUrlThumbnail() {
        return urlThumbnail;
    }

    public void setUrlThumbnail(String urlThumbnail) {
        this.urlThumbnail = urlThumbnail;
    }

    public String getUrlOriginal() {
        return urlOriginal;
    }

    public void setUrlOriginal(String urlOriginal) {
        this.urlOriginal = urlOriginal;
    }

    public String getUrlLargeSize() {
        return urlLargeSize;
    }

    public void setUrlLargeSize(String urlLargeSize) {
        this.urlLargeSize = urlLargeSize;
    }

    public String getUrlMediumSize() {
        return urlMediumSize;
    }

    public void setUrlMediumSize(String urlMediumSize) {
        this.urlMediumSize = urlMediumSize;
    }

    public String getUrlSmallSize() {
        return urlSmallSize;
    }

    public void setUrlSmallSize(String urlSmallSize) {
        this.urlSmallSize = urlSmallSize;
    }

    public static String createId(){
        UUID id = UUID.randomUUID();
        return id.toString();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
