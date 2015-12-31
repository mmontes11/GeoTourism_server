package com.mmontes.rest.controller;

import com.mmontes.model.service.RouteService;
import com.mmontes.model.service.TIPService;
import com.mmontes.rest.request.RouteRequest;
import com.mmontes.service.GoogleMapsService;
import com.mmontes.util.GeometryUtils;
import com.mmontes.util.dto.RouteDetailsDto;
import com.mmontes.util.exception.GeometryParsingException;
import com.mmontes.util.exception.GoogleMapsServiceException;
import com.mmontes.util.exception.InstanceNotFoundException;
import com.vividsolutions.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RouteController {

    @Autowired
    private GoogleMapsService googleMapsService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private TIPService tipService;

    @RequestMapping(value = "/social/route", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RouteDetailsDto>
    create(@RequestBody RouteRequest routeRequest,
           @RequestParam(value = "facebookUserId", required = false) Long facebookUserId) {

        if (routeRequest.getName() == null || routeRequest.getName().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (routeRequest.getDescription() == null || routeRequest.getDescription().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (!googleMapsService.isValidTravelMode(routeRequest.getTravelMode())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Geometry routeGeom = null;
        if (routeRequest.getLineStrings() != null) {
            if (routeRequest.getLineStrings().size() < 2) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                List<Geometry> lineStrings = new ArrayList<>();
                try {
                    for (String geometryString : routeRequest.getLineStrings()) {
                        Geometry lineString = GeometryUtils.geometryFromWKT(geometryString);
                        lineStrings.add(lineString);
                    }
                    routeGeom = GeometryUtils.unionGeometries(lineStrings);
                    if (!tipService.geometryContainsTIPs(routeGeom, routeRequest.getTipIds())) {
                        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                    }
                } catch (GeometryParsingException e) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
        }
        if (routeRequest.getTipIds() == null || routeRequest.getTipIds().size() < 2) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String name = routeRequest.getName();
        String description = routeRequest.getDescription();
        String travelMode = routeRequest.getTravelMode();
        List<Long> tipdIds = routeRequest.getTipIds();
        RouteDetailsDto routeDetailsDto;
        try {
            routeDetailsDto = routeService.create(name, description, travelMode, routeGeom, tipdIds, facebookUserId);
        } catch (GeometryParsingException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (GoogleMapsServiceException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(routeDetailsDto, HttpStatus.CREATED);
    }

}
