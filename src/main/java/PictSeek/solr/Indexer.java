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
import java.util.stream.Stream;

import static org.apache.solr.cli.SolrCLI.getSolrClient;

public class Indexer {
    private static Logger log = LoggerFactory.getLogger(Indexer.class);

    private static final String SOLR_URL = ServiceConfig.getSolrUrl();
    private static final String COLLECTION = ServiceConfig.getSolrCollection();


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
    public static void index(MetadataDocument doc) {

        final SolrClient client = getSolrClient(SOLR_URL);

        final SolrInputDocument solrDoc = new SolrInputDocument();
        solrDoc.addField("id", doc.getId());
        solrDoc.addField("imageWidth", doc.getImageWidth());
        solrDoc.addField("imageLength", doc.getImageLength());

        try {
            final UpdateResponse updateResponse = client.add(COLLECTION, solrDoc);
            // Indexed documents must be committed
            client.commit(COLLECTION);
        } catch (SolrServerException | IOException e) {
            log.warn("Error occurred when adding and committing solr document to solr. Client URL is: '{}'", client.getContext().toString());
            throw new RuntimeException(e);
        }
    }
}
