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


}
