package com.mmontes.rest.controller;

import com.mmontes.model.service.internal.CityService;
import com.mmontes.util.GeometryConversor;
import com.mmontes.util.exception.GeometryParsingException;
import com.vividsolutions.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CityController {

    @Autowired
    private CityService cityService;

    @RequestMapping(value = "/city", method = RequestMethod.GET)
    public ResponseEntity<String>
    isLocatedInExistingCity(@RequestParam(value = "location", required = true) String locationString) {
        Geometry geometry;
        try {
            geometry = GeometryConversor.geometryFromWKT(locationString);
        } catch (GeometryParsingException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (cityService.isLocatedInExistingCity(geometry)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
