package PictSeek.ingest;

import PictSeek.config.ServiceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Array;

/**
 * A Java wrapper for the excellent <a href="https://github.com/icloud-photos-downloader/icloud_photos_downloader">iCloud Photos Downloader</a>.
 */
public class IcloudIngester {
    private static Logger log = LoggerFactory.getLogger(IcloudIngester.class);

    public static String ingest() throws IOException {
        String icloudDownloader = "icloudpd";
        String directoryArg = "--directory";
        String absoluteImageFolder = ServiceConfig.getIcloudDownloadDirectory();
        String userArg = "--username";
        String userInput = ServiceConfig.getIcloudUser();
        String passArg = "--password";
        String passInput = ServiceConfig.getIcloudPass();
        String recentArg = "--recent";
        String recentInput = "10";
        String skipVideos = "--skip-videos";
        String folderStructureArg = "--folder-structure";
        String folderStructureInput = "none";

        String[] completeCommand = {icloudDownloader, directoryArg, absoluteImageFolder, userArg, userInput,
                                    passArg, passInput, recentArg, recentInput, skipVideos, folderStructureArg,
                                    folderStructureInput};


        log.debug("Starting image ingest from iCloud");
        Runtime rt = Runtime.getRuntime();
        Process pr = rt.exec(completeCommand);

        log.info("Finished image ingest from iCloud.");

        return "Success";
    }

}
