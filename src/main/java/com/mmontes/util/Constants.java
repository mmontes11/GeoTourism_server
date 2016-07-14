package com.mmontes.util;
public class Constants {

    public static final String SPRING_CONFIG_FILE = "classpath:/spring-config.xml";

    public static final int SRID = 4326;
    public static final int MIN_DISTANCE = 100;
    public static final int MAX_POINTS_ROUTE = 10;
    public static final int MAX_RATING_VALUE = 5;

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static final String GCM_TYPES_UPDATED = "TYPES_UPDATED";

    public static final String OSM_BASE_URL = "http://api.openstreetmap.fr/xapi?node[bbox=%s][%s=%s]";
    public static final String OSM_POLYGONS_BASE_URL = "http://polygons.openstreetmap.fr";
    public static final String GCM_BASE_URL = "https://android.googleapis.com/gcm/send";
    public static final String GMAPS_BASE_URL = "http://maps.google.com/maps";
    public static final String WIKIPEDIA_SEARCH_URL = ".wikipedia.org/w/api.php?action=opensearch&search=";

    public static final String CRON_SYNC_TIPS = "";
    public static final String CRON_SYNC_ADDRESSES = "";
}
