package PictSeek.api.v1.impl;

import PictSeek.api.v1.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import PictSeek.ingest.IcloudIngester;
import dk.kb.util.webservice.exception.ServiceException;
import dk.kb.util.webservice.exception.InternalServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.io.File;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dk.kb.util.webservice.ImplBase;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Providers;
import javax.ws.rs.core.MediaType;
import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;
import org.apache.cxf.jaxrs.ext.MessageContext;
import org.apache.cxf.jaxrs.ext.multipart.*;

import io.swagger.annotations.Api;

/**
 * PictSeek Archive
 *
 * <p>This is the API for a home imageserver.
 *
 */
public class PictSeekApiServiceImpl extends ImplBase implements PictSeekApi {
    private Logger log = LoggerFactory.getLogger(this.toString());



    /**
     * Create content description for images.
     * 
     * @return <ul>
      *   <li>code = 200, message = "Created content descriptions for all images without descriptions already.", response = String.class</li>
      *   </ul>
      * @throws ServiceException when other http codes should be returned
      *
      * Update images without content descriptions with AI generated descriptions.
      *
      * @implNote return will always produce a HTTP 200 code. Throw ServiceException if you need to return other codes
     */
    @Override
    public String createContentDescriptions() throws ServiceException {
        // TODO: Implement...
    
        
        try { 
            String response = "yiY9zQ";
        return response;
        } catch (Exception e){
            throw handleException(e);
        }
    
    }

    /**
     * Ingest images from iCloud
     * 
     * @param user: 
     * 
     * @param password: 
     * 
     * @param ingestType: 
     * 
     * @return <ul>
      *   <li>code = 200, message = "Ingest from iCloud was a success.", response = String.class</li>
      *   </ul>
      * @throws ServiceException when other http codes should be returned
      *
      * Ingest images from iCloud for a specific user.
      *
      * @implNote return will always produce a HTTP 200 code. Throw ServiceException if you need to return other codes
     */
    @Override
    public String ingestIcloud() throws ServiceException {
        try {
            log.info("Starting ingester");
            String result = IcloudIngester.ingest();
        return result;
        } catch (Exception e){
            throw handleException(e);
        }
    
    }

    /**
     * Add metadata documents to solr.
     * 
     * @param fromTime: 
     * 
     * @return <ul>
      *   <li>code = 200, message = "Successfully indexed records.", response = String.class</li>
      *   </ul>
      * @throws ServiceException when other http codes should be returned
      *
      * Update solr index with either new records or reindexs all records.
      *
      * @implNote return will always produce a HTTP 200 code. Throw ServiceException if you need to return other codes
     */
    @Override
    public String solrIndex(BigDecimal fromTime) throws ServiceException {
        // TODO: Implement...
        log.info("testing solr endpoint");
    
        
        try { 
            String response = "result";
        return response;
        } catch (Exception e){
            throw handleException(e);
        }
    
    }


}
