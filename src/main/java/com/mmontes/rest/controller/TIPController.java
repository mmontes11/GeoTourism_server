package com.mmontes.rest.controller;

import com.mmontes.model.entity.TIP.TIP;
import com.mmontes.service.internal.TIPService;
import com.mmontes.util.GeometryConversor;
import com.mmontes.util.dto.TIPDto;
import com.mmontes.util.exception.GeometryParsingException;
import com.vividsolutions.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tip")
public class TIPController {

    @Autowired
    private TIPService tipService;

    @RequestMapping( method= RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String>
                find(@RequestParam(value="facebookUserId", required = false) Long facebookUserId,
                     @RequestParam(value="locationString", required = false) String locationString,
                     @RequestParam(value="type", required = false) String type,
                     @RequestParam(value="cityId", required = false) Long cityId,
                     @RequestParam(value="favouritedBy", required = false) Integer favouritedBy,
                     @RequestParam(value="radius", required = false) Double radius){

        System.out.println("Request params:");
        System.out.println(facebookUserId);
        System.out.println(locationString);
        System.out.println(type);
        System.out.println(cityId);
        System.out.println(favouritedBy);
        System.out.println(radius);

        //TODO: validate params

        Point location = null;
        try {
            if (locationString != null){
                location = GeometryConversor.pointFromText(locationString);
            }
        } catch (GeometryParsingException e) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }

        List<TIPDto> tip = tipService.find(facebookUserId,location,type,cityId,favouritedBy,radius);

        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
