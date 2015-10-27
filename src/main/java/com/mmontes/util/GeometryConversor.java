package com.mmontes.util;

import com.mmontes.util.exception.GeometryParsingException;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.io.WKTWriter;

import static com.mmontes.util.Constants.SRID;

public class GeometryConversor {

    public static Geometry geometryFromWKT(String wkt) throws GeometryParsingException {
        WKTReader wktReader = new WKTReader();
        Geometry geometry;
        try {
            geometry = wktReader.read(wkt);
            geometry.setSRID(SRID);
            return geometry;
        } catch (com.vividsolutions.jts.io.ParseException e) {
            throw new GeometryParsingException(e.getMessage());
        }
    }

    public static String wktFromGeometry(Geometry geometry){
        WKTWriter wktWriter = new WKTWriter();
        return wktWriter.write(geometry);
    }

}

