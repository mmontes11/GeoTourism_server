package com.mmontes.model.service.external;

import com.vividsolutions.jts.geom.Coordinate;

public class GoogleMapsService {

    public static String getTIPGoogleMapsUrl(Coordinate coordinate){
        return "http://maps.google.com/maps?q=loc:"+coordinate.y+","+coordinate.x;
    }
}
