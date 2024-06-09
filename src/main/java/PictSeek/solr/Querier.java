package PictSeek.solr;

import PictSeek.config.ServiceConfig;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;

import java.io.IOException;
import java.util.stream.Stream;

import static org.apache.solr.cli.SolrCLI.getSolrClient;

/**
 * Class containing methods to query solr.
 */
public class Querier {
    final static SolrClient solrClient = getSolrClient(ServiceConfig.getSolrUrl());

    /**
     * Get count of documents in solr.
     * @return amount of documents in solr
     */
    public static long getCountOfDocuments() throws SolrServerException, IOException {

        SolrQuery query = new SolrQuery();
        query.setQuery("*:*"); // Query all documents

        QueryResponse response = solrClient.query(ServiceConfig.getSolrCollection(), query);

        SolrDocumentList documents = response.getResults();

        return documents.getNumFound();

    }

    /**
     * Get all documents, where description is equal to "".
     * @return a stream of SolrDocuments without values for the description field.
     */
    public static Stream<SolrDocument> getDocumentsWithoutDescription() throws SolrServerException, IOException {
        SolrQuery query = new SolrQuery();

        ModifiableSolrParams solrParams = new ModifiableSolrParams();
        solrParams.add("description", "");

        QueryResponse response = solrClient.query(ServiceConfig.getSolrCollection(), query);

        SolrDocumentList documents = response.getResults();
        return documents.stream();
    }
}
