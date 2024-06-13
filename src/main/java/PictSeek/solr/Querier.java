package PictSeek.solr;

import PictSeek.config.ServiceConfig;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Stream;

import static org.apache.solr.cli.SolrCLI.getSolrClient;

/**
 * Class containing methods to query solr.
 */
public class Querier {
    private static Logger log = LoggerFactory.getLogger(Querier.class);

    final static SolrClient solrClient = getSolrClient(ServiceConfig.getSolrUrl());
    private static final String COLLECTION = ServiceConfig.getSolrCollection();


    public static String select(String query) throws SolrServerException, IOException {
        SolrQuery solrQuery = new SolrQuery(query);

        QueryResponse response = solrClient.query(COLLECTION, solrQuery);

        return response.jsonStr();
    }

    /**
     * Get count of documents in solr.
     * @return amount of documents in solr
     */
    public static long getCountOfDocuments() throws SolrServerException, IOException {

        SolrQuery query = new SolrQuery();
        query.setQuery("*:*"); // Query all documents

        QueryResponse response = solrClient.query(COLLECTION, query);

        SolrDocumentList documents = response.getResults();

        return documents.getNumFound();

    }

    /**
     * Get all documents, where description is equal to "".
     * @return a stream of SolrDocuments without values for the description field.
     */
    public static Stream<SolrDocument> getDocumentsWithoutDescription() throws SolrServerException, IOException {
        SolrQuery query = new SolrQuery();
         query.setQuery("description:\"EMPTY\"");
         //TODO: Implement correct retrieval of all records.
         query.setRows(2147483647);

        QueryResponse response = solrClient.query(ServiceConfig.getSolrCollection(), query);
        log.info("Solr query found '{}' records.", response.getResults().getNumFound());

        SolrDocumentList documents = response.getResults();
        return documents.stream();
    }
}

