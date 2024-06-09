package PictSeek.solr;

import PictSeek.Facade;
import PictSeek.config.ServiceConfig;
import PictSeek.ingest.ImageIngester;
import PictSeek.metadata.MetadataDocument;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.apache.solr.cli.SolrCLI.getSolrClient;

/**
 * Class containing methods to index to solr.
 */
public class Indexer {
    private static Logger log = LoggerFactory.getLogger(Indexer.class);

    /**
     * Base URL for solr.
     */
    private static final String SOLR_URL = ServiceConfig.getSolrUrl();
    private static final String COLLECTION = ServiceConfig.getSolrCollection();

    /**
     * Solr client for the base url.
     */
    private final static SolrClient client = getSolrClient(SOLR_URL);

    /**
     * Image specific indexing method.
     * Takes all files from the path specified in the configuration as ingest.to and creates SolrDocuments for these
     * images. Then indexes all to solr.
     */
    public static void indexPhotosFromDirectory() throws IOException {
        Path directory = Path.of(ServiceConfig.getIngestTo());

        // Ensure the path is a directory
        if (!Files.isDirectory(directory)) {
            throw new IllegalArgumentException("Provided path is not a directory: " + directory);
        }

        // For each image in the imageserver create a solr document and add it to solr.
        try (Stream<Path> files = Files.list(directory)) {
            files
                .map(file -> Facade.createSolrDocumentForImage(file.toString()))
                .forEach(Indexer::index);
        }
    }

    /**
     * Create a {@link SolrInputDocument} from the input {@link MetadataDocument}
     * @param doc containing values that are to be added to solr.
     */
    public static void index(MetadataDocument doc) {
        final SolrInputDocument solrDoc = new SolrInputDocument();
        solrDoc.addField("id", doc.getId());
        solrDoc.addField("imageWidth", doc.getImageWidth());
        solrDoc.addField("imageLength", doc.getImageLength());
        solrDoc.addField("urlThumbnail", doc.getUrlThumbnail());
        solrDoc.addField("urlOriginal", doc.getUrlOriginal());
        solrDoc.addField("urlLargeSize", doc.getUrlLargeSize());
        solrDoc.addField("urlMediumSize", doc.getUrlMediumSize());
        solrDoc.addField("urlSmallSize", doc.getUrlSmallSize());
        solrDoc.addField("description", doc.getDescription());

        try {
            final UpdateResponse updateResponse = client.add(COLLECTION, solrDoc);
            // Indexed documents must be committed
            client.commit(COLLECTION);
        } catch (SolrServerException | IOException e) {
            log.warn("Error occurred when adding and committing solr document to solr.");
            throw new RuntimeException(e);
        }
    }

    /**
     * Update the description field for a single record in solr.
     * @param id of the document to update.
     * @param descriptionValue the new value for the description field.
     */
    public static void updateDescription(String id, String descriptionValue){
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", id); // Specify the ID of the document to update

        // Atomic update: increment a field
        Map<String, String> incrementOperation = new HashMap<>();
        incrementOperation.put("set", descriptionValue);
        doc.addField("description", incrementOperation);

        try {
            client.add(COLLECTION, doc);
            client.commit(COLLECTION);
        } catch (SolrServerException | IOException e) {
            log.warn("Error occurred when updating description for document with ID: '{}'.", id);
            throw new RuntimeException(e);
        }

    }
}
