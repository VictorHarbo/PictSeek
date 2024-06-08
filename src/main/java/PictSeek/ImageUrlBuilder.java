package PictSeek;

import PictSeek.config.ServiceConfig;
import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;

public class ImageUrlBuilder {
    private static String baseUrl = ServiceConfig.getImageserver();


    public static String createUrlOriginal(String imagePath) throws URISyntaxException {
        URIBuilder builder = createFifOnlyUri(imagePath);
        builder.addParameter("CVT", "jpeg");
        return builder.build().toString();
    }

    public static String createUrlLarge(String imagePath) throws URISyntaxException {
        URIBuilder builder = createFifOnlyUri(imagePath);
        builder.addParameter("WID", "2400");
        builder.addParameter("CVT", "jpeg");
        return builder.build().toString();
    }
    public static String createUrlMedium(String imagePath) throws URISyntaxException {
        URIBuilder builder = createFifOnlyUri(imagePath);
        builder.addParameter("WID", "1920");
        builder.addParameter("CVT", "jpeg");
        return builder.build().toString();
    }

    public static String createUrlSmall(String imagePath) throws URISyntaxException {
        URIBuilder builder = createFifOnlyUri(imagePath);
        builder.addParameter("WID", "640");
        builder.addParameter("CVT", "jpeg");
        return builder.build().toString();
    }

    public static String createUrlThumbnail(String imagePath) throws URISyntaxException {
        URIBuilder builder = createFifOnlyUri(imagePath);
        builder.addParameter("WID", "240");
        builder.addParameter("CVT", "jpeg");
        return builder.build().toString();
    }





    public static URIBuilder createFifOnlyUri(String imagePath) throws URISyntaxException {
        String fileName = imagePath.substring(imagePath.lastIndexOf("/"));

        URIBuilder builder = new URIBuilder(baseUrl);
        builder.addParameter("FIF", fileName);
        return builder;
    }


}
