package com.mmontes.rest.controller;

import com.mmontes.model.service.internal.TIPService;
import com.mmontes.model.util.TIPUtils;
import com.mmontes.rest.request.TIPPatchRequest;
import com.mmontes.rest.request.TIPRequest;
import com.mmontes.util.GeometryConversor;
import com.mmontes.util.dto.TIPDto;
import com.mmontes.util.exception.AmazonServiceExeption;
import com.mmontes.util.exception.GeometryParsingException;
import com.mmontes.util.exception.InstanceNotFoundException;
import com.vividsolutions.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TIPController {

    @Autowired
    private TIPService tipService;

    @RequestMapping(value = "/admin/tip", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TIPDto>
    create(@RequestBody TIPRequest tipRequest) {

        if (!TIPUtils.isValidType(tipRequest.getType()) ||
                tipRequest.getName() == null || tipRequest.getName().isEmpty() ||
                tipRequest.getGeometry() == null || tipRequest.getGeometry().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Geometry geometry;
        try {
            geometry = GeometryConversor.geometryFromWKT(tipRequest.getGeometry());
        } catch (GeometryParsingException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        TIPDto tipDto;
        try {
            tipDto = tipService.create(tipRequest.getType(), tipRequest.getName(), tipRequest.getDescription(),
                    tipRequest.getPhotoUrl(), tipRequest.getPhotoContent(), tipRequest.getPhotoName(),
                    tipRequest.getInfoUrl(), geometry);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(tipDto, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/admin/tip/{TIPId}", method = RequestMethod.GET)
    public ResponseEntity<TIPDto>
    find(@PathVariable Long TIPId) {
        TIPDto tipDto;
        try {
            tipDto = tipService.findById(TIPId);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tipDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/tip/{TIPId}", method = RequestMethod.DELETE)
    public ResponseEntity
    delete(@PathVariable Long TIPId) {
        try {
            tipService.remove(TIPId);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/tip/{TIPId}", method = RequestMethod.PATCH)
    public ResponseEntity
    patch(@PathVariable Long TIPId,
          @RequestBody TIPPatchRequest tipPatchRequest ) {
        try {
            tipService.edit(TIPId, tipPatchRequest);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (AmazonServiceExeption e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}