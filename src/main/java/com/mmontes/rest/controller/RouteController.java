package com.mmontes.rest.controller;

import com.mmontes.model.service.RouteService;
import com.mmontes.rest.request.RoutePatchRequest;
import com.mmontes.rest.request.RouteRequest;
import com.mmontes.rest.response.IntegerResult;
import com.mmontes.rest.response.ResponseFactory;
import com.mmontes.service.FacebookService;
import com.mmontes.util.Constants;
import com.mmontes.util.GeometryUtils;
import com.mmontes.util.dto.RouteDetailsDto;
import com.mmontes.util.exception.GeometryParsingException;
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
    private RouteService routeService;

    @RequestMapping(value = "/social/route", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RouteDetailsDto>
    create(@RequestBody RouteRequest routeRequest,
           @RequestHeader(value = "FacebookUserId", required = true) Long facebookUserId) {

        if (routeRequest.getName() == null || routeRequest.getName().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Geometry> partialGeoms = null;
        if (routeRequest.getLineStrings() != null && !routeRequest.getLineStrings().isEmpty()) {
            partialGeoms = new ArrayList<>();
            try {
                for (String geometryString : routeRequest.getLineStrings()) {
                    Geometry lineString = GeometryUtils.geometryFromWKT(geometryString);
                    partialGeoms.add(lineString);
                }
            } catch (GeometryParsingException e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        if (routeRequest.getTipIds() != null && routeRequest.getTipIds().size() > Constants.MAX_POINTS_ROUTE){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        RouteDetailsDto routeDetailsDto;
        try {
            routeDetailsDto = routeService.create(routeRequest.getName(), routeRequest.getDescription(), routeRequest.getTravelMode(), partialGeoms, routeRequest.getTipIds(), facebookUserId);
        } catch (InvalidRouteException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(routeDetailsDto, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/social/route/{routeID}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RouteDetailsDto>
    patch(@PathVariable Long routeID,
          @RequestBody RoutePatchRequest routePatchRequest,
          @RequestHeader(value = "FacebookUserId", required = true) Long facebookUserId){
        RouteDetailsDto routeDetailsDto;
        try {
            routeDetailsDto = routeService.edit(routeID,facebookUserId,
                    routePatchRequest.getName(),routePatchRequest.getDescription(),routePatchRequest.getTravelMode(),routePatchRequest.getTipIds());
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (InvalidRouteException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(routeDetailsDto, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/route/{routeID}", method = RequestMethod.GET)
    public ResponseEntity<RouteDetailsDto>
    findById(@PathVariable Long routeID,
             @RequestHeader(value="AuthorizationFB", required = false) String accessToken,
             @RequestHeader(value = "FacebookUserId", required = false) Long facebookUserId){
        if (!FacebookService.validFBparams(accessToken,facebookUserId)){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        RouteDetailsDto routeDetailsDto;
        try {
            routeDetailsDto = routeService.findById(routeID,facebookUserId);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(routeDetailsDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/social/route/{routeID}", method = RequestMethod.DELETE)
    public ResponseEntity
    delete(@PathVariable Long routeID,
           @RequestHeader(value = "FacebookUserId", required = true)Long facebookUserId){
        try {
            routeService.remove(routeID,facebookUserId);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/social/route/geom", method = RequestMethod.GET)
    public ResponseEntity
    getShortestPath(@RequestParam(value = "origin", required = true) Long TIPIdOrigin,
                    @RequestParam(value = "destination", required = true) Long TIPIdDestination,
                    @RequestParam(value = "travelMode", required = true) String travelMode){
        try {
            Geometry geom = routeService.getShortestPath(TIPIdOrigin,TIPIdDestination,travelMode);
            String geomWKT = GeometryUtils.WKTFromGeometry(geom);
            return new ResponseEntity<>(ResponseFactory.getCustomJSON("geom",geomWKT),HttpStatus.OK);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (InvalidRouteException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/social/route/maxpoints", method = RequestMethod.GET)
    public ResponseEntity<IntegerResult>
    getMaxWayPoints(){
        return new ResponseEntity<>(new IntegerResult(Constants.MAX_POINTS_ROUTE),HttpStatus.OK);
    }
}
