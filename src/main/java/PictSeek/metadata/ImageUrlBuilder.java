package PictSeek.metadata;

import PictSeek.config.ServiceConfig;
import org.apache.http.client.utils.URIBuilder;

import java.net.URISyntaxException;

/**
 * Create different URLs for a SolrDocument.
 * Sizes small, medium and large have been constructed from the sizes used at Unsplash.com
 */
public class ImageUrlBuilder {

    /**
     * Base URL for the imageserver.
     */
    private static final String baseUrl = ServiceConfig.getImageserver();


    /**
     * Create URL for full size image.
     */
    public static String createUrlOriginal(String imagePath) throws URISyntaxException {
        URIBuilder builder = createFifOnlyUri(imagePath);
        builder.addParameter("CVT", "jpeg");
        return builder.build().toString();
    }

    /**
     * Create URL for large image.
     */
    public static String createUrlLarge(String imagePath) throws URISyntaxException {
        URIBuilder builder = createFifOnlyUri(imagePath);
        builder.addParameter("WID", "2400");
        builder.addParameter("CVT", "jpeg");
        return builder.build().toString();
    }

    /**
     * Create URL for medium image.
     */
    public static String createUrlMedium(String imagePath) throws URISyntaxException {
        URIBuilder builder = createFifOnlyUri(imagePath);
        builder.addParameter("WID", "1920");
        builder.addParameter("CVT", "jpeg");
        return builder.build().toString();
    }

    /**
     * Create URL for small image.
     */
    public static String createUrlSmall(String imagePath) throws URISyntaxException {
        URIBuilder builder = createFifOnlyUri(imagePath);
        builder.addParameter("WID", "640");
        builder.addParameter("CVT", "jpeg");
        return builder.build().toString();
    }

    /**
     * Create thumbnail URL.
     */
    public static String createUrlThumbnail(String imagePath) throws URISyntaxException {
        URIBuilder builder = createFifOnlyUri(imagePath);
        builder.addParameter("WID", "240");
        builder.addParameter("CVT", "jpeg");
        return builder.build().toString();
    }


    /**
     * Create FIF only URL used to build all other URLS.
     * @return a {@link URIBuilder} which can be extended with more IIPImage parameters.
     */
    public static URIBuilder createFifOnlyUri(String imagePath) throws URISyntaxException {
        String fileName = imagePath.substring(imagePath.lastIndexOf("/"));

        URIBuilder builder = new URIBuilder(baseUrl);
        builder.addParameter("FIF", fileName);
        return builder;
    }


}
