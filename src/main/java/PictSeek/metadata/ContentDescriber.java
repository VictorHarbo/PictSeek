package PictSeek.metadata;

import com.azure.ai.vision.imageanalysis.*;
import com.azure.ai.vision.imageanalysis.models.*;
import com.azure.core.credential.KeyCredential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;

public class ContentDescriber {
    private static Logger log = LoggerFactory.getLogger(ContentDescriber.class);

    public static void createDescriptionForImages(){
        //TODO: Query solr for images without description through solr.Querier and get URL to image in small or medium size.
        // Store ID for updating later.

        // Then for each image
        //TODO: Load image from url into memory.
        //TODO: Query AZURE VISION for a description for the image.

        //TODO: Update description in solr through solr.Indexer
    }

    public static void example() throws IOException {
        String endpoint = System.getenv("VISION_ENDPOINT");
        String key = System.getenv("VISION_KEY");

        if (endpoint == null || key == null) {
            log.warn("Missing environment variable 'VISION_ENDPOINT' or 'VISION_KEY'.");
            throw new IOException("Missing environment variable 'VISION_ENDPOINT' or 'VISION_KEY'.");
        }

        // Create a synchronous Image Analysis client.
        ImageAnalysisClient client = new ImageAnalysisClientBuilder()
                .endpoint(endpoint)
                .credential(new KeyCredential(key))
                .buildClient();


        // This is a synchronous (blocking) call.
        ImageAnalysisResult result = client.analyzeFromUrl(
                "https://learn.microsoft.com/azure/ai-services/computer-vision/media/quickstarts/presentation.png",
                Arrays.asList(VisualFeatures.CAPTION, VisualFeatures.READ),
                new ImageAnalysisOptions().setGenderNeutralCaption(true));

        // Print analysis results to the console
        System.out.println("Image analysis results:");
        System.out.println(" Caption:");
        System.out.println("   \"" + result.getCaption().getText() + "\", Confidence "
                + String.format("%.4f", result.getCaption().getConfidence()));
        System.out.println(" Read:");
        for (DetectedTextLine line : result.getRead().getBlocks().get(0).getLines()) {
            System.out.println("   Line: '" + line.getText()
                    + "', Bounding polygon " + line.getBoundingPolygon());
            for (DetectedTextWord word : line.getWords()) {
                System.out.println("     Word: '" + word.getText()
                        + "', Bounding polygon " + word.getBoundingPolygon()
                        + ", Confidence " + String.format("%.4f", word.getConfidence()));
            }
        }

    }
}
