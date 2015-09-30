package com.mmontes.util;

import com.mmontes.util.exception.GeometryParsingException;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.io.WKTWriter;
import org.geotools.geojson.GeoJSON;
import org.geotools.geojson.geom.GeometryJSON;
import org.geotools.geometry.text.WKTParser;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import static com.mmontes.util.Constants.SRID_INSERT;

public class GeometryConversor {

    public static Geometry geometryFromWKT(String wkt) throws GeometryParsingException {
        WKTReader wktReader = new WKTReader();
        Geometry geometry;
        try {
            geometry = wktReader.read(wkt);
            geometry.setSRID(SRID_INSERT);
            return geometry;
        } catch (com.vividsolutions.jts.io.ParseException e) {
            throw new GeometryParsingException(e.getMessage());
        }
    }

    public static String wktFromGeometry(Geometry geometry){
        WKTWriter wktWriter = new WKTWriter();
        return wktWriter.write(geometry);
    }

    public static Point pointFromText(String pointString) throws GeometryParsingException {
        String[] coordinates = pointString.split(",");
        if (coordinates.length != 2) {
            throw new GeometryParsingException(pointString);
        }
        Double x;
        Double y;
        try {
            x = Double.parseDouble(coordinates[0]);
            y = Double.parseDouble(coordinates[1]);
        } catch (NumberFormatException e) {
            throw new GeometryParsingException(pointString);
        }
        Coordinate coordinate = new Coordinate(x, y);
        GeometryFactory geometryFactory = new GeometryFactory();
        Point point = geometryFactory.createPoint(coordinate);
        point.setSRID(SRID_INSERT);
        return point;
    }
/*
    public static Geometry geometryFromGeoJSON(GeoJSON geoJSON) throws GeometryParsingException {
        GeometryJSON geometryJSON = new GeometryJSON();
        try {
            Geometry geometry = geometryJSON.read(geoJSON);
            geometry.setSRID(SRID_INSERT);
            return geometry;
        } catch (IOException e) {
            throw new GeometryParsingException(e.getMessage());
        }
    }

    public static Geometry geometryFromGeoJSONText(String geoJSON) throws GeometryParsingException {
        GeometryJSON geometryJSON = new GeometryJSON();
        Reader reader = new StringReader(geoJSON);
        try {
            Geometry geometry = geometryJSON.read(reader);
            geometry.setSRID(SRID_INSERT);
            return geometry;
        } catch (IOException e) {
            throw new GeometryParsingException(geoJSON);
        }
    }
*/

}

