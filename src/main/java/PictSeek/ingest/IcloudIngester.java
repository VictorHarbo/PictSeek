package PictSeek.ingest;

import PictSeek.config.ServiceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * A Java wrapper for the excellent <a href="https://github.com/icloud-photos-downloader/icloud_photos_downloader">iCloud Photos Downloader</a>.
 */
public class IcloudIngester {
    private static Logger log = LoggerFactory.getLogger(IcloudIngester.class);
    private static String absoluteImageFolder = ServiceConfig.getIcloudDownloadDirectory();

    public static String ingest() throws IOException, InterruptedException {
        //downloadFromICloud();

        convertToTIFF();

        return "Success";
    }

    private static void convertToTIFF() throws IOException {
        Path dir = Paths.get(absoluteImageFolder);

        try (Stream<Path> stream = Files.walk(dir)) {
            stream.filter(Files::isRegularFile)
                    .filter(IcloudIngester::checkExtension)
                    .forEach(System.out::println);
        }

        // TODO: Implement this command in the stream above.
        //vips im_vips2tiff <source_image> <output_image.tif>:<compression>,tile:<size>,pyramid
    }

    private static boolean checkExtension(Path file) {
        String filename = file.toString();
        return filename.endsWith("HEIC") || filename.endsWith("PNG");
    }

    private static void downloadFromICloud() throws IOException, InterruptedException {
        String icloudDownloader = "icloudpd";
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
    }

}
