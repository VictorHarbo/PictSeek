import PictSeek.metadata.MetadataDocument;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.core.CoreContainer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

public class EmbeddedSolrTest {
    private static final Logger log = LoggerFactory.getLogger(EmbeddedSolrTest.class);
    private static final String solr_home = "target/solr";

    private static CoreContainer coreContainer = null;
    private static EmbeddedSolrServer embeddedServer = null;




    @Test
    public void testSolrRunning() throws SolrServerException, IOException {
        embeddedServer.ping("PictSeek");

        SolrInputDocument doc = new SolrInputDocument();
        doc.setField("id", "testId");
        doc.setField("imageLength", 200);
        doc.setField("imageWidth", 400);
        embeddedServer.add(doc);

        embeddedServer.getById("testId");
    }

    @Test
    public void addingDocumentFromPOJO() throws IOException {
        MetadataDocument document = new MetadataDocument();
        document.setId("testId");
        document.setImageWidth(400);
        document.setImageLength(500);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        String jsonString = objectMapper.writeValueAsString(document);

        addRecordToEmbeddedServer(jsonString);
    }


    @BeforeAll
    public static void startEmbeddedSolrServer() {
        File solrHomeDir = new File(solr_home);
        String solrHomeAbsoluteDir= solrHomeDir.getAbsolutePath();
        Path solrHome =  Paths.get(solrHomeAbsoluteDir);
        System.setProperty("solr.install.dir", solrHomeAbsoluteDir);
        Properties props = new Properties();

        coreContainer = new CoreContainer(solrHome, props);
        coreContainer.load();
        embeddedServer = new EmbeddedSolrServer(coreContainer, "PictSeek");
    }

    @AfterAll
    public static void tearDown() throws IOException {
        coreContainer.shutdown();
        embeddedServer.close();
    }

    /*
     * Delete all documents in the embedded solr between tests, so that each unittest gets a fresh and empty solr.
     */
    @BeforeEach
    public void deleteDocs() throws SolrServerException, IOException {
        embeddedServer.deleteByQuery("*:*");
    }

    /**
     * Adds a SolrJSON document to the embedded solr server.
     * @param jsonSolrString    containing the solr json representation of the record.
     */
    private void addRecordToEmbeddedServer(String jsonSolrString) throws IOException {
        SolrInputDocument document = convertJsonToSolrJavaDoc(jsonSolrString);
        try {
            embeddedServer.add(document);
            embeddedServer.commit();
        } catch (SolrServerException e) {
            throw new IOException("Unable to add Solr document '" + jsonSolrString + "' to embedded Solr server", e);
        }
    }

    /**
     * Convert a JSON string to a {@link SolrInputDocument}
     */
    private SolrInputDocument convertJsonToSolrJavaDoc(String json) throws IOException {
        Map<String, Object> map = new ObjectMapper().readValue(json, new TypeReference<>(){});
        SolrInputDocument document = new SolrInputDocument();

        for (String key : map.keySet()) {
            //Object can be String, Integer or String[]
            Object  value = map.get(key);
            if (value instanceof String || value instanceof Integer) {
                document.addField(key, map.get(key));
            }
            else if (value instanceof ArrayList) {
                for (Object o : (ArrayList<Object>) value) {
                    document.addField(key, o.toString());
                }
            }
            else { // This should not happen. Propably happens because something hasn't been handled properly above
                throw new IOException("Error creating SolrInputDocument, please check that the following class is" +
                        " handled correctly: " + value.getClass());
            }
        }
        return document;
    }
}
