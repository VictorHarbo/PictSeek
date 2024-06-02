package PictSeek.ingest;

import PictSeek.config.ServiceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * A Java wrapper for the excellent <a href="https://github.com/icloud-photos-downloader/icloud_photos_downloader">iCloud Photos Downloader</a>.
 */
public class IcloudIngester {
    private static Logger log = LoggerFactory.getLogger(IcloudIngester.class);

    public static String ingest() throws IOException, InterruptedException {
        String icloudDownloader = "icloudpd";
        String absoluteImageFolder = ServiceConfig.getIcloudDownloadDirectory();
        String userInput = ServiceConfig.getIcloudUser();
        String passInput = ServiceConfig.getIcloudPass();
        String recentArg = "--recent";
        String recentInput = "10";
        String skipVideos = "--skip-videos";
        String folderStructureArg = "--folder-structure";
        String folderStructureInput = "none";

        String[] completeCommand = {icloudDownloader, "--directory", absoluteImageFolder, "--username", userInput,
                "--password", passInput, recentArg, recentInput, skipVideos, folderStructureArg,
                                    folderStructureInput};


        log.debug("Starting image ingest from iCloud for user: '{}'", userInput);
        Runtime rt = Runtime.getRuntime();
        Process pr = rt.exec(completeCommand);

        pr.waitFor();
        log.info("Finished image ingest from iCloud.");

        return "Success";
    }

}
