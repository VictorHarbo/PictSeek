package PictSeek.config;

import dk.kb.util.yaml.YAML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ServiceConfig {
    private static final Logger log = LoggerFactory.getLogger(ServiceConfig.class);

    /**
     * Besides parsing of YAML files using SnakeYAML, the YAML helper class provides convenience
     * methods like {@code getInteger("someKey", defaultValue)} and {@code getSubMap("config.sub1.sub2")}.
     */
    private static YAML serviceConfig;

    /**
     * Initialized the configuration from the provided configFile.
     * This should normally be called from as
     * part of web server initialization of the container.
     * @param configFiles the configuration to load.
     * @throws IOException if the configuration could not be loaded or parsed.
     */
    public static synchronized void initialize(String... configFiles) throws IOException {
        serviceConfig = YAML.resolveLayeredConfigs(configFiles);
        serviceConfig.setExtrapolate(true);
    }

    public static String getIngestFrom(){
        return (String) serviceConfig.get("ingest.from");
    }

    public static String getIngestTo(){
        return (String) serviceConfig.get("ingest.to");
    }

    public static String getSolrUrl(){
        return (String) serviceConfig.get("solr.url");
    }

    public static String getSolrCollection(){
        return (String) serviceConfig.get("solr.collection");
    }
    public static String getImageserver(){
        return serviceConfig.getString("imageserver");
    }

    public static String getAzureEndpoint(){
        return serviceConfig.getString("azure.endpoint");
    }

    public static String getAzureKey(){
        return serviceConfig.getString("azure.key");
    }

    public static String getDbUrl(){
        return serviceConfig.getString("database.url");
    }
    public static String getDbUser(){
        return serviceConfig.getString("database.user");
    }
    public static String getDbPassword(){
        return serviceConfig.getString("database.password");

    }
}
