package com.mmontes.rest.controller;

import com.mmontes.model.service.internal.TIPService;
import com.mmontes.util.GeometryConversor;
import com.mmontes.util.dto.TIPDetailsDto;
import com.mmontes.util.dto.TIPSearchDto;
import com.mmontes.util.exception.GeometryParsingException;
import com.vividsolutions.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TIPsController {

    @Autowired
    private TIPService tipService;

    @RequestMapping(value = "/tips", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<TIPSearchDto>>
    find(@RequestParam(value = "facebookUserId", required = false) Long facebookUserId,
         @RequestParam(value = "location", required = false) String locationString,
         @RequestParam(value = "type", required = false) String type,
         @RequestParam(value = "cityId", required = false) Long cityId,
         @RequestParam(value = "favouritedBy", required = false) Integer favouritedBy,
         @RequestParam(value = "radius", required = false) Double radius) {

        System.out.println("Find TIPs:");
        System.out.println(facebookUserId);
        System.out.println(locationString);
        System.out.println(type);
        System.out.println(cityId);
        System.out.println(favouritedBy);
        System.out.println(radius);

        //TODO: validate params

        Point location = null;
        try {
            if (locationString != null) {
                location = GeometryConversor.pointFromText(locationString);
            }
        } catch (GeometryParsingException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<TIPSearchDto> tips = tipService.find(facebookUserId, location, type, cityId, favouritedBy, radius);

        return new ResponseEntity<>(tips, HttpStatus.OK);
    }
}
