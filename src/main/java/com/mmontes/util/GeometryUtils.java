package com.mmontes.util;

import com.google.maps.model.LatLng;
import com.mmontes.util.exception.GeometryParsingException;
import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.io.WKTWriter;
import org.geotools.geometry.jts.JTSFactoryFinder;

import java.util.ArrayList;
import java.util.List;

import static com.mmontes.util.Constants.SRID;

public class GeometryUtils {

    public static Geometry geometryFromWKT(String wkt) throws GeometryParsingException {
        if(wkt == null || wkt.equals("")){
            throw new GeometryParsingException("Unable to parse empty or null geometry");
        }
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

    public static String WKTFromGeometry(Geometry geometry){
        WKTWriter wktWriter = new WKTWriter();
        return wktWriter.write(geometry);
    }

    public static Geometry geometryFromListLatLng(List<LatLng> latLngs){
        ArrayList<Coordinate> coordinateList = new ArrayList<>();
        for (LatLng latLng : latLngs){
            coordinateList.add(new Coordinate(latLng.lng,latLng.lat));
        }
        Coordinate[] coordinates = coordinateList.toArray(new Coordinate[coordinateList.size()]);
        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
        LineString lineString = geometryFactory.createLineString(coordinates);
        lineString.setSRID(SRID);
        return lineString;
    }

    public static Geometry unionGeometries(List<Geometry> geometries) throws GeometryParsingException {
        Geometry all = null;
        for (Geometry geometry : geometries){
            if( geometry == null ) continue;
            if( all == null ){
                all = geometry;
            }
            else {
                all = all.union( geometry );
            }
        }
        all.setSRID(SRID);
        return all;
    }
}

