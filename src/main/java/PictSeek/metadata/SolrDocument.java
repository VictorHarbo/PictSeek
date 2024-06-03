package PictSeek.metadata;

public class SolrDocument {
    private String id;
    private Integer imageHeight;
    private Integer imageLength;

    public SolrDocument(String id, Integer imageHeight, Integer imageLength) {
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

    public Integer getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(Integer imageHeight) {
        this.imageHeight = imageHeight;
    }

    public Integer getImageLength() {
        return imageLength;
    }

    public void setImageLength(Integer imageLength) {
        this.imageLength = imageLength;
    }
}
