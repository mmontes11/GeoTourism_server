package com.mmontes.rest.controller;

import com.mmontes.model.util.TIPUtils;
import com.mmontes.rest.request.TIPRequest;
import com.mmontes.rest.response.ResponseFactory;
import com.mmontes.model.service.internal.TIPService;
import com.mmontes.util.GeometryConversor;
import com.mmontes.util.dto.TIPDto;
import com.mmontes.util.exception.GeometryParsingException;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TIPController {

    @Autowired
    private TIPService tipService;

    @RequestMapping(value = "/admin/tip", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JSONObject>
        create(@RequestBody TIPRequest tipRequest) {

        System.out.println("Create TIP:");
        System.out.println(tipRequest);

        if (!TIPUtils.isValidType(tipRequest.getType()) ||
                tipRequest.getName() == null || tipRequest.getName().isEmpty() ||
                tipRequest.getGeometry() == null || tipRequest.getGeometry().isEmpty()) {
            return new ResponseEntity<>(ResponseFactory.getErrorResponse("Type, Name and Geometry are mandatory"), HttpStatus.BAD_REQUEST);
        }
        Geometry geometry;
        try {
            geometry = GeometryConversor.geometryFromWKT(tipRequest.getGeometry());
        } catch (GeometryParsingException e) {
            e.printStackTrace();
            return new ResponseEntity<>(ResponseFactory.getErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        TIPDto tipDto;
        try {
            tipDto = tipService.create(tipRequest.getType(), tipRequest.getName(), tipRequest.getDescription(),
                    tipRequest.getPhotoUrl(), tipRequest.getPhotoContent(), tipRequest.getExtension(),
                    tipRequest.getInfoUrl(), geometry);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(ResponseFactory.getErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ResponseFactory.geCreatedResponse(tipDto.getId()), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/tip", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<TIPDto>>
        find(@RequestParam(value = "facebookUserId", required = false) Long facebookUserId,
             @RequestParam(value = "locationString", required = false) String locationString,
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

        List<TIPDto> tips = tipService.find(facebookUserId, location, type, cityId, favouritedBy, radius);

        return new ResponseEntity<>(tips,HttpStatus.OK);
    }
}
