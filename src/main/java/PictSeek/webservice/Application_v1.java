package PictSeek.webservice;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import PictSeek.api.v1.impl.PictSeekApiServiceImpl;
import PictSeek.api.v1.impl.ServiceApiServiceImpl;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import dk.kb.util.webservice.OpenApiResource;
import dk.kb.util.webservice.exception.ServiceExceptionMapper;


public class Application_v1 extends javax.ws.rs.core.Application {

    @Override
    public Set<Class<?>> getClasses() {

        return new HashSet<>(Arrays.asList(
                JacksonJsonProvider.class,
                PictSeekApiServiceImpl.class,
                ServiceApiServiceImpl.class,
                ServiceExceptionMapper.class,
                OpenApiResource.class
        ));
    }


}
