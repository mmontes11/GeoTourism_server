package com.mmontes.service;

import com.mmontes.util.Constants;
import com.mmontes.util.GeometryUtils;
import com.vividsolutions.jts.geom.Geometry;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;

@Service("OpenStreetMapService")
public class OpenStreetMapService {

    private HttpURLConnection makeRequest(String url) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection connnection = (HttpURLConnection) obj.openConnection();
        connnection.setRequestMethod("GET");
        connnection.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = connnection.getResponseCode();
        if (responseCode >= 400) {
            throw new Exception("Request to OpenStreetMap failed");
        }
        return connnection;
    }

    public Geometry getGeometryByOSMId(Long osmId) throws Exception {
        String generatePolygonUrl = Constants.OSM_POLYGONS_BASE_URL + "/?id="+osmId;
        String getPolygonUrl = Constants.OSM_POLYGONS_BASE_URL + "/get_wkt.py?id=" + osmId + "&params=0";

        makeRequest(generatePolygonUrl);
        HttpURLConnection connection = makeRequest(getPolygonUrl);

        String geomWKT = IOUtils.toString(connection.getInputStream(), "UTF8");
        Geometry geomCity = GeometryUtils.geometryFromWKT(geomWKT.split(";")[1]);
        geomCity.setSRID(Constants.SRID);
        return geomCity;
    }
}
