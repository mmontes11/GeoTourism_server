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
import com.mmontes.util.exception.InvalidRouteException;
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
        List<Geometry> partialGeoms = new ArrayList<>();
        if (routeRequest.getLineStrings() != null) {
            try {
                for (String geometryString : routeRequest.getLineStrings()) {
                    Geometry lineString = GeometryUtils.geometryFromWKT(geometryString);
                    partialGeoms.add(lineString);
                }
            } catch (GeometryParsingException e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        RouteDetailsDto routeDetailsDto;
        try {
            routeDetailsDto = routeService.create(routeRequest.getName(), routeRequest.getDescription(), routeRequest.getTravelMode(), partialGeoms, routeRequest.getTipIds(), facebookUserId);
        } catch (InvalidRouteException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (GoogleMapsServiceException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(routeDetailsDto, HttpStatus.CREATED);
    }

}
