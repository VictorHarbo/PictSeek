package PictSeek.ingest;

import PictSeek.config.ServiceConfig;
import PictSeek.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Ingest images of supported type into the backing IIPImage server. The images are converted to tiff and ingested.
 * When the images have been ingested to the server, all content in the ingest directory is deleted.
 */
public class ImageIngester {
    private static Logger log = LoggerFactory.getLogger(ImageIngester.class);
    private static final String INGEST_FROM = ServiceConfig.getIngestFrom();

    private static ImageType imageType;

    /**
     * Enum for determining the type of the current image.
     */
    private enum ImageType {
        HEIC,
        PNG
    }

    /**
     * Main method of the ImageIngester. This method should be the only public method in the class.
     * The job of the  ImageIngester, is to convert images from input formats and serve them as TIFF files ready for
     * showing through an imageserver supporting TIFFs such as IIPImage. 
     * @return A string with the text "Success" to indicate that the process has finished correctly.
     */
    public static String ingest() throws IOException {
        convertToTIFF();

        // TODO: Add support for deleting from temporary HEIC/PNG folder
        //deleteTemporaryFiles();

        return "Success";
    }

    /**
     * Converts all images in the configured ingest directory to TIFF format and add them to the specified directory
     * for TIFF images, that are to be served by the image server.
     */
    private static void convertToTIFF() throws IOException {
        Path dir = Paths.get(INGEST_FROM);

        try (Stream<Path> stream = Files.walk(dir)) {
            stream.filter(Files::isRegularFile)
                    .filter(ImageIngester::checkExtension)
                    .forEach(ImageIngester::createTiffWithVIPS);
        }
    }

    /**
     * Perform a command line call to <a href="https://www.libvips.org/">VIPS</a> and convert a single image to TIFF
     * and save it to the configured {@code tiffDirectory}.
     * Supports the following file formats:
     * <ul>
     *     <li>HEIC</li>
     *     <li>PNG</li>
     * </ul>
     * @param inputImagePath the path of the image to convert.
     */
    private static void createTiffWithVIPS(Path inputImagePath) {

        String tiffPath = constructTiffPath(inputImagePath);

        String[] vipsCommand = getVipsCommand(inputImagePath, tiffPath);

        // Create a new process builder
        ProcessBuilder processBuilder = new ProcessBuilder(vipsCommand);

        try {
            // Start the process
            Process process = processBuilder.start();

            // Create threads to handle the process' output and error streams
            new Thread(() -> Util.logStream(process.getInputStream())).start();
            new Thread(() -> Util.logStream(process.getErrorStream())).start();

            // Wait for the process to complete
            int exitCode = process.waitFor();

            if (exitCode != 0){
                log.warn("VIPS Process exited with code: " + exitCode);
            }

        } catch (IOException | InterruptedException e) {
            log.warn("An error occurred when trying to create .tiff from image: ''{}", inputImagePath);
            throw new RuntimeException(e);
        }
    }

    public static void deleteTemporaryFiles() throws IOException {
        Path directory = Path.of(ServiceConfig.getIngestFrom());

        // Ensure the path is a directory
        if (!Files.isDirectory(directory)) {
            throw new IllegalArgumentException("Provided path is not a directory: " + directory);
        }

        // Get a stream of paths in the directory
        try (Stream<Path> files = Files.list(directory)) {
            files.forEach(ImageIngester::deleteFile);
        }
    }

    private static void deleteFile(Path file) {
        try {
            // Delete each file
            if (Files.isRegularFile(file)) {
                Files.delete(file);
            }
        } catch (IOException e) {
            log.warn("Failed to delete file: " + file + " - " + e.getMessage());
        }
    }

    /**
     * Construct the Command Line argument for VIPS.
     * @param inputImagePath path of the input image.
     * @param tiffPath where the created TIFF image is saved.
     * @return a string array containing all parts of the command to use with the {@link Runtime#exec(String[])}-method.
     */
    private static String[] getVipsCommand(Path inputImagePath, String tiffPath) {
        String[] vipsCommand = null;
        if (imageType.equals(ImageType.PNG)) {
            // Convert standard image formats to TIFF.
            String tiffWithArguments = tiffPath + ":deflate,tile:256x256,pyramid";
            vipsCommand = new String[]{"vips", "im_vips2tiff", inputImagePath.toString(), tiffWithArguments};
        } else if (imageType.equals(ImageType.HEIC)) {
            // Convert HEIC (Apple images) to TIFF.
            vipsCommand = new String[]{"vips", "tiffsave", inputImagePath.toString(), tiffPath,
                    "--tile",  "--pyramid", "--compression=jpeg", "--tile-width=256", "--tile-height=256"};
        }

        if (vipsCommand == null){
            throw new RuntimeException("Error converting input image to .tiff as the format of the file isn't supported " +
                    "by the application, yet.");
        }
        log.debug("Using VIPS command: '{}'", Arrays.toString(vipsCommand));
        return vipsCommand;
    }

    /**
     * Create a path for a TIFF image in the directory specified in the configuration.
     * The file is named with the same name as the input image.
     * @param inputImagePath path to the image being converted to TIFF.
     * @return a name for the converted TIFF in the configured directory.
     */
    private static String constructTiffPath(Path inputImagePath) {
        String originalFilename = inputImagePath.getFileName().toString();

        String filenameNoEnding = originalFilename.substring(0, originalFilename.indexOf("."));
        return ServiceConfig.getIngestTo() + "/" + filenameNoEnding + ".tiff";
    }

    /**
     * Check extension of an input file. If it is a valid format, return true and set imageType enum accordingly.
     * @param file to check extension for.
     * @return true if extension is valid.
     */
    private static boolean checkExtension(Path file) {
        String filename = file.toString();
        if (filename.toLowerCase().endsWith(".heic")){
            imageType = ImageType.HEIC;
        } else if (filename.toLowerCase().endsWith(".png")) {
            imageType = ImageType.PNG;
        }

        return filename.endsWith("HEIC") || filename.endsWith("PNG");
    }

}
