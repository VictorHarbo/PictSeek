package PictSeek.api.v1.impl;

import PictSeek.api.v1.*;

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
 * PictSeek
 *
 * <p>This is the API for a home imageserver.
 *
 */
public class ServiceApiServiceImpl extends ImplBase implements ServiceApi {
    private Logger log = LoggerFactory.getLogger(this.toString());



    /**
     * Ping the server
     * 
     * @return <ul>
      *   <li>code = 200, message = "A pong response", response = String.class</li>
      *   </ul>
      * @throws ServiceException when other http codes should be returned
      *
      * Returns a pong message
      *
      * @implNote return will always produce a HTTP 200 code. Throw ServiceException if you need to return other codes
     */
    @Override
    public String ping() throws ServiceException {
        log.info("ping endpoint has been called.");
    
        return "pong";
    }


}
