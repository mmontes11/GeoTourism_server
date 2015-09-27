package com.mmontes.util;

import com.vividsolutions.jts.geom.Geometry;
import org.geotools.geojson.geom.GeometryJSON;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import static com.mmontes.util.Constants.*;

public class GeometryConversor {

    public static Geometry geometryFromGeoJSON(String geoJSON){
        GeometryJSON geometryJSON = new GeometryJSON();
        Reader reader = new StringReader(geoJSON);
        try {
            Geometry geometry = geometryJSON.read(reader);
            geometry.setSRID(SRID_INSERT);
            return geometry;
        } catch (IOException e) {
            return null;
        }
    }
}

