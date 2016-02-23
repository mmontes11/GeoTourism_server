package com.mmontes.util;

import com.google.maps.model.LatLng;
import com.mmontes.util.exception.GeometryParsingException;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.io.WKTWriter;

import java.util.ArrayList;
import java.util.List;

import static com.mmontes.util.Constants.SRID;

public class GeometryUtils {

    public enum GeomOperation {
        UNION,
        INTERSECTION
    }

    public static Geometry geometryFromWKT(String wkt) throws GeometryParsingException {
        if (wkt == null || wkt.equals("")) {
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

    public static List<Geometry> listGeometryFromListWKT(List<String> WKTs) throws GeometryParsingException {
        List<Geometry> partialGeoms = null;
        if (WKTs != null && !WKTs.isEmpty()) {
            partialGeoms = new ArrayList<>();
            for (String geometryString : WKTs) {
                Geometry lineString = GeometryUtils.geometryFromWKT(geometryString);
                partialGeoms.add(lineString);
            }
        }
        return partialGeoms;
    }

    public static String WKTFromGeometry(Geometry geometry) {
        WKTWriter wktWriter = new WKTWriter();
        return wktWriter.write(geometry);
    }

    public static Geometry geometryFromListLatLng(List<LatLng> latLngs) {
        ArrayList<Coordinate> coordinateList = new ArrayList<>();
        for (LatLng latLng : latLngs) {
            coordinateList.add(new Coordinate(latLng.lng, latLng.lat));
        }
        System.out.println(coordinateList);
        Coordinate[] coordinates = coordinateList.toArray(new Coordinate[coordinateList.size()]);
        LineString lineString = new GeometryFactory().createLineString(coordinates);
        lineString.setSRID(SRID);
        return lineString;
    }

    public static Geometry apply(List<Geometry> geometries, GeomOperation operation) {
        Geometry all = null;
        for (Geometry geometry : geometries) {
            if (geometry == null) continue;
            if (all == null) {
                all = geometry;
            } else {
                switch (operation) {
                    case UNION:
                        all = all.union(geometry);
                        break;
                    case INTERSECTION:
                        all = all.intersection(geometry);
                        break;
                    default:
                        return null;
                }
            }
        }
        if (all != null) {
            all.setSRID(SRID);
        }
        return all;
    }

    public static String getBBoxString(Geometry geom){
        final Coordinate[] coords = geom.getCoordinates();
        List<Coordinate> bboxCoords = new ArrayList<Coordinate>() {{
            add(coords[0]);
            add(coords[2]);
        }};
        StringBuilder bboxString = new StringBuilder();
        for (int i = 0; i < bboxCoords.size(); i++) {
            Coordinate c = bboxCoords.get(i);
            bboxString.append(c.x).append(",").append(c.y);
            if (i != (bboxCoords.size() - 1)) {
                bboxString.append(",");
            }
        }
        return bboxString.toString();
    }

    public static Geometry latLong2Geom(String lon,String lat) throws GeometryParsingException {
        String geomWKT = "POINT("+lon+" "+lat+")";
        return geometryFromWKT(geomWKT);
    }
}

