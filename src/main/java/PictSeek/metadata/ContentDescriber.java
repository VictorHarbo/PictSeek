package PictSeek.metadata;

import PictSeek.config.ServiceConfig;
import PictSeek.solr.Indexer;
import PictSeek.solr.Querier;
import com.azure.ai.vision.imageanalysis.*;
import com.azure.ai.vision.imageanalysis.models.*;
import com.azure.core.credential.KeyCredential;
import com.azure.core.util.BinaryData;
import io.swagger.models.auth.In;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Describe content in images through the Microsoft Azure Vision AI.
 */
public class ContentDescriber {
    private static Logger log = LoggerFactory.getLogger(ContentDescriber.class);
    private static final String endpoint = ServiceConfig.getAzureEndpoint();
    private static final String key = ServiceConfig.getAzureKey();

    private static final ImageAnalysisClient client = getImageAnalysisClient(endpoint, key);


    /**
     * Create a description for each image in the solr index not containing a description.
     * This is done in three steps:
     *  <ol>
     *      <li>Query solr for images with description = ""</li>
     *      <li>For each image call the Azure Vision API</li>
     *      <li>Add the updated description to the document in solr</li>
     *  </ol>
     */
    public static void createDescriptionForImages() throws SolrServerException, IOException {
        //TODO: Query solr for images without description through solr.Querier and get URL to image in small or medium size.
        Stream<SolrDocument> documents = Querier.getDocumentsWithoutDescription();

        // Store ID for updating later.

        //TODO: Handle all methods below in the same .forEach streaming step.
        documents.forEach(ContentDescriber::createDescription);
    }

    private static void createDescription(SolrDocument doc) {
        String id = (String) doc.get("id");
        // Load image from url into memory.
        InputStream image;
        try {
            image = loadImageFromServer((String) doc.get("urlMediumSize"));
        } catch (IOException e) {
            log.warn("Could not load image from image server.");
            throw new RuntimeException(e);
        }

        try {
            log.debug("Image Inputstream has '{}' bytes available.", image.available());

            if (image.available() == 0){
                log.warn("Image Inputstream seems empty. Loading of image might have failed.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        // Query AZURE VISION for a description for the image.
        ImageAnalysisResult result = analyzeImageFromMemory(image);

        if (result.getCaption().getText().isEmpty()){
            log.warn("Analysis of image has completed with an empty caption.");

        }

        // Update description in solr through solr.Indexer
        Indexer.updateDescription(id, result.getCaption().getText());

        //TODO: Add a tags field to solr and update it here.
    }

    private static InputStream loadImageFromServer(String urlString) throws IOException {
        log.debug("Loading image from URL: '{}'.", urlString);
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        return conn.getInputStream();
    }

    public static ImageAnalysisResult analyzeImageFromMemory(InputStream image){
        BinaryData imageBinary = BinaryData.fromStream(image);
        return client.analyze(imageBinary,
                Arrays.asList(VisualFeatures.CAPTION, VisualFeatures.OBJECTS),
                new ImageAnalysisOptions().setGenderNeutralCaption(true));

    }


    public static void example() throws IOException {
        if (endpoint == null || key == null) {
            log.warn("Missing environment variable 'VISION_ENDPOINT' or 'VISION_KEY'.");
            throw new IOException("Missing environment variable 'VISION_ENDPOINT' or 'VISION_KEY'.");
        }

        // This is a synchronous (blocking) call.
        ImageAnalysisResult result = client.analyzeFromUrl(
                "https://learn.microsoft.com/azure/ai-services/computer-vision/media/quickstarts/presentation.png",
                Arrays.asList(VisualFeatures.CAPTION, VisualFeatures.OBJECTS),
                new ImageAnalysisOptions().setGenderNeutralCaption(true));


        // Print analysis results to the console
        System.out.println("Image analysis results:");
        System.out.println(" Caption:");
        System.out.println("   \"" + result.getCaption().getText() + "\", Confidence "
                + String.format("%.4f", result.getCaption().getConfidence()));

        for (DetectedObject object : result.getObjects().getValues()){
            System.out.println("Object: " + object.toString());
            for (DetectedTag tag : object.getTags()){
                System.out.println("Tag: " + tag.getName());
            }
        }
    }

    private static ImageAnalysisClient getImageAnalysisClient(String endpoint, String key) {
        // Create a synchronous Image Analysis client.
        ImageAnalysisClient client = new ImageAnalysisClientBuilder()
                .endpoint(endpoint)
                .credential(new KeyCredential(key))
                .buildClient();
        return client;
    }
}
