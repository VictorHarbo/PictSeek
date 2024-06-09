import PictSeek.Facade;
import PictSeek.config.ServiceConfig;
import PictSeek.metadata.MetadataDocument;
import PictSeek.solr.Indexer;
import PictSeek.solr.Querier;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class SolrIntegrationTest {

    @BeforeAll
    static void setup() throws IOException {
        ServiceConfig.initialize("conf/PictSeek-local.yaml");
    }

    @Test
    public void testAddingDocument() {
        MetadataDocument doc = Facade.createSolrDocumentForImage("src/test/resources/testImage.tiff");
        doc.setId(MetadataDocument.createId());
        Indexer.index(doc);
    }

    @Test
    public void testQuery() throws SolrServerException, IOException {
        long result = Querier.getCountOfDocuments();
        System.out.println("Found " + result + " documents");
    }

    @Test
    public void testDescriptionUpdate(){
        Indexer.updateDescription("5f900164-a655-4d09-a6f4-5c4ef68fba36", "This is a test description.");
    }
}
