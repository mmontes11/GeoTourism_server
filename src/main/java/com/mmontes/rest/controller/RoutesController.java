package com.mmontes.rest.controller;

import com.mmontes.model.service.CityService;
import com.mmontes.model.service.RouteService;
import com.mmontes.service.FacebookService;
import com.mmontes.util.GeometryUtils;
import com.mmontes.util.dto.FeatureSearchDto;
import com.mmontes.util.exception.GeometryParsingException;
import com.mmontes.util.exception.InstanceNotFoundException;
import com.vividsolutions.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RoutesController {

    @Autowired
    private RouteService routeService;

    @Autowired
    private CityService cityService;

    @RequestMapping(value = "/routes", method = RequestMethod.GET)
    public ResponseEntity<List<FeatureSearchDto>>
    find(@RequestParam(value = "bounds", required = false) String boundsWKT,
         @RequestParam(value = "travelModes", required = false) List<String> travelModes,
         @RequestParam(value = "cityIds", required = false) List<Long> cityIds,
         @RequestParam(value = "createdBy", required = false) Integer createdBy,
         @RequestHeader(value = "AuthorizationFB", required = false) String accessToken,
         @RequestParam(value = "facebookUserId", required = false) Long facebookUserId,
         @RequestParam(value = "friends", required = false) List<Long> friendsFacebookUserIds) {

        if (!FacebookService.validFBparams(accessToken, facebookUserId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Geometry bounds = null;
        if (boundsWKT != null) {
            try {
                bounds = GeometryUtils.geometryFromWKT(boundsWKT);
            } catch(GeometryParsingException e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        Geometry unionCities = null;
        if (cityIds != null && !cityIds.isEmpty()){
            try {
                unionCities= cityService.getGeomUnionCities(cityIds);
            } catch (InstanceNotFoundException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        if (createdBy != null && createdBy != 0 && createdBy != 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Geometry> geoms = new ArrayList<>();
        geoms.add(bounds);
        geoms.add(unionCities);
        Geometry geom = GeometryUtils.apply(geoms,GeometryUtils.GeomOperation.INTERSECTION);
        String geomWKT = GeometryUtils.WKTFromGeometry(geom);

        List<FeatureSearchDto> routes;
        try {
            routes = routeService.find(geomWKT,travelModes,createdBy,facebookUserId,friendsFacebookUserIds);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(routes,HttpStatus.OK);
    }
}