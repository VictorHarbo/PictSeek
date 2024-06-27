package PictSeek.database;

import java.util.UUID;

public class ImageRecord {
    private final int id;
    private final UUID uuid;
    private final String url;
    private final int imageWidth;
    private final int imageLength;
    private final String description;
    private final String[] tags;

    public ImageRecord(int id, UUID uuid, String url, int imageWidth, int imageLength, String description, String[] tags) {
        this.id = id;
        this.uuid = uuid;
        this.url = url;
        this.imageWidth = imageWidth;
        this.imageLength = imageLength;
        this.description = description;
        this.tags = tags;
    }

    // Getters
    public int getId() {
        return id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getUrl() {
        return url;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageLength() {
        return imageLength;
    }

    public String getDescription() {
        return description;
    }

    public Object[] getTags() {
        return tags;
    }

    // toString method for easy printing
    @Override
    public String toString() {
        return "ImageRecord{" +
                "id=" + id +
                ", uuid=" + uuid +
                ", url='" + url + '\'' +
                ", imageWidth=" + imageWidth +
                ", imageLength=" + imageLength +
                ", description='" + description + '\'' +
                ", tags=" + String.join(",", (CharSequence[]) tags) +
                '}';
    }
}
