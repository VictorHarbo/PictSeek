package PictSeek.database;

import PictSeek.config.ServiceConfig;

import java.sql.*;
import java.util.UUID;

public class ImageDatabase {

    private final String url;
    private final String user;
    private final String password;

    // Constructor
    public ImageDatabase() {
        this.url = ServiceConfig.getDbUrl();
        this.user = ServiceConfig.getDbUser();
        this.password = ServiceConfig.getDbPassword();
    }

    // Get a record by ID
    public ImageRecord getRecord(int id) throws SQLException {
        String query = "SELECT * FROM images WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new ImageRecord(
                            rs.getInt("id"),
                            UUID.fromString(rs.getString("uuid")),
                            rs.getString("url"),
                            rs.getInt("imagewidth"),
                            rs.getInt("imagelength"),
                            rs.getString("description"),
                            (String[]) rs.getArray("tags").getArray()
                    );
                } else {
                    return null;
                }
            }
        }
    }

    // Update descriptions for all records without descriptions
    public int updateDescriptionsForRecordsWithoutDescriptions(String newDescription) throws SQLException {
        String query = "UPDATE images SET description = ? WHERE description IS NULL OR description = ''";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newDescription);
            return stmt.executeUpdate();
        }
    }

    // Add a new record
    public void addRecord(String url, int imageWidth, int imageLength, String description, String[] tags) throws SQLException {
        String query = "INSERT INTO images (uuid, url, imagewidth, imagelength, description, tags) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(this.url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setObject(1, UUID.randomUUID());
            stmt.setString(2, url);
            stmt.setInt(3, imageWidth);
            stmt.setInt(4, imageLength);
            stmt.setString(5, description);
            stmt.setArray(6, conn.createArrayOf("text", tags));
            stmt.executeUpdate();
        }
    }

}
