package com.mmontes.util;

public class GlobalNames {

    public static final String SPRING_CONFIG_FILE = "classpath:/spring/spring-config.xml" ;

    public static final String MONUMENT_DISCRIMINATOR = "M";
    public static final String NATURAL_SPACE_DISCRIMINATOR = "NS";
    public static final String HOTEL_DISCRIMINATOR = "H";
    public static final String RESTAURANT_DISCRIMINATOR = "R";

    public static final double SEARCH_RADIUS_METRES = 100;

    public static final String AMAZON_S3_BUCKET_NAME = "geotourism";
    public static final String AMAZON_S3_ROOT_URL = "https://s3.amazonaws.com/" + AMAZON_S3_BUCKET_NAME + "/";

    public GlobalNames() {
    }
}
