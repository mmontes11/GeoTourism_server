package com.mmontes.util;

import com.mmontes.util.exception.GeometryParsingException;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import org.geotools.geojson.geom.GeometryJSON;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import static com.mmontes.util.Constants.*;

public class GeometryConversor {

    public static Geometry geometryFromGeoJSON(String geoJSON) throws GeometryParsingException {
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

    public static Point pointFromText (String pointString) throws GeometryParsingException {
        String[] coordinates = pointString.split(",");
        if (coordinates.length != 2){
            throw new GeometryParsingException(pointString);
        }
        Double x;
        Double y;
        try {
            x = Double.parseDouble(coordinates[0]);
            y = Double.parseDouble(coordinates[1]);
        }catch(NumberFormatException e){
            throw new GeometryParsingException(pointString);
        }
        Coordinate coordinate = new Coordinate(x,y);
        GeometryFactory geometryFactory = new GeometryFactory();
        Point point = geometryFactory.createPoint(coordinate);
        point.setSRID(SRID_INSERT);
        return point;
    }
}

