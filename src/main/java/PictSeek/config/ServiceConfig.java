package PictSeek.config;

import dk.kb.util.yaml.YAML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

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

    public static String getIcloudUser(){
        return (String) serviceConfig.get("icloudIngester.user");
    }
    public static String getIcloudPass(){
        return (String) serviceConfig.get("icloudIngester.password");

    }
    public static String getIcloudDownloadDirectory(){
        return (String) serviceConfig.get("icloudIngester.downloadDirectory");
    }

    public static String getTiffDirectory(){
        return (String) serviceConfig.get("icloudIngester.tiffDirectory");
    }
    public static boolean ICloudDownloaderIsDocker() {
        return serviceConfig.getBoolean("icloudIngester.dockerImage", true);
    }
}
