package com.mmontes.service;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.mmontes.util.Constants;
import com.mmontes.util.GeometryConversor;
import com.mmontes.util.exception.GoogleMapsServiceException;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service("GoogleMapsService")
public class GoogleMapsService {

    private GeoApiContext context;

    public GoogleMapsService() {
        this.context = new GeoApiContext();
        this.context.setApiKey(Constants.GOOGLE_MAPS_KEY)
                .setQueryRateLimit(3)
                .setConnectTimeout(1, TimeUnit.SECONDS)
                .setReadTimeout(1, TimeUnit.SECONDS)
                .setWriteTimeout(1, TimeUnit.SECONDS);
    }

    public String getTIPGoogleMapsUrl(Coordinate coordinate) {
        return "http://maps.google.com/maps?q=loc:" + coordinate.y + "," + coordinate.x;
    }

    public String getRouteGoogleMapsUrl(List<Coordinate> coordinates){
        String routeUrl = "http://maps.google.com/maps?";
        for (int i = 0; i<coordinates.size(); i++){
            Coordinate coordinate = coordinates.get(i);
            String coordinateString = coordinate.y + "," + coordinate.x;
            if (i == 0){
                routeUrl += "saddr=" + coordinateString;
            }else if (i == 1){
                routeUrl += "&daddr=" + coordinateString;
            } else {
                routeUrl += ":to=" + coordinateString;
            }
        }
        return routeUrl;
    }

    public String getAddress(Coordinate coordinate) throws GoogleMapsServiceException {
        GeocodingResult[] results;
        try {
            results = GeocodingApi.newRequest(context)
                    .latlng(new LatLng(coordinate.y, coordinate.x))
                    .await();
        } catch (Exception e) {
            throw new GoogleMapsServiceException();
        }
        if (results.length == 0){
            throw new GoogleMapsServiceException();
        }
        return results[0].formattedAddress;
    }

    public Geometry getRoute(List<Coordinate> coordinates) throws GoogleMapsServiceException {
        ArrayList<String> wayPointsList = new ArrayList<>();
        for (int i = 1; i < coordinates.size() - 1; i++) {
            Coordinate coordinate = coordinates.get(i);
            String coordinateString = coordinate.y + "," + coordinate.x;
            wayPointsList.add(coordinateString);
        }
        String[] wayPoints = wayPointsList.toArray(new String[wayPointsList.size()]);

        Coordinate originCoordinate = coordinates.get(0);
        LatLng origin = new LatLng(originCoordinate.y, originCoordinate.x);
        Coordinate destinationCoordinate = coordinates.get(coordinates.size() - 1);
        LatLng destination = new LatLng(destinationCoordinate.y, destinationCoordinate.x);

        DirectionsRoute[] results;
        try {
            results = DirectionsApi.newRequest(context)
                    .origin(origin)
                    .destination(destination)
                    .waypoints(wayPoints)
                    .await();
        } catch (Exception e) {
            throw new GoogleMapsServiceException();
        }
        if (results.length == 0){
            throw new GoogleMapsServiceException();
        }
        return GeometryConversor.geometryFromListLatLng(results[0].overviewPolyline.decodePath());
    }
}
