package com.mmontes.util;

import com.vividsolutions.jts.geom.Geometry;
import org.geotools.geojson.geom.GeometryJSON;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class GeometryConversor {

    public static Geometry geometryFromGeoJSON(String geoJSON){
        GeometryJSON geometryJSON = new GeometryJSON();
        Reader reader = new StringReader(geoJSON);
        try {
            return geometryJSON.read(reader);
        } catch (IOException e) {
            return null;
        }
    }
}

