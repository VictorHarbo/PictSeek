package PictSeek.metadata;

public class SolrDocument {
    private String id;
    private String imageHeight;
    private String imageLength;

    public SolrDocument(String id, String imageHeight, String imageLength) {
        this.id = id;
        this.imageHeight = imageHeight;
        this.imageLength = imageLength;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(String imageHeight) {
        this.imageHeight = imageHeight;
    }

    public String getImageLength() {
        return imageLength;
    }

    public void setImageLength(String imageLength) {
        this.imageLength = imageLength;
    }
}
