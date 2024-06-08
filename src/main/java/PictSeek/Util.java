package PictSeek;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Util {
    private static Logger log = LoggerFactory.getLogger(Util.class);

    /**
     * Method used to log content from processes run by non-java dependencies such as VIPS or iCloud Photo Downloader
     * @param stream to read from, which is logged on debug level.
     */
    public static void logStream(InputStream stream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                log.debug(line);
            }
        } catch (IOException e) {
            log.warn("Exception occurred while reading stream", e);
        }
    }
}
