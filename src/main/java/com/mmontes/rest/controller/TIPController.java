package com.mmontes.rest.controller;

import com.mmontes.model.entity.TIPtype;
import com.mmontes.model.service.TIPService;
import com.mmontes.model.service.TIPtypeService;
import com.mmontes.rest.request.TIPPatchRequest;
import com.mmontes.rest.request.TIPRequest;
import com.mmontes.rest.response.ResponseFactory;
import com.mmontes.util.GeometryConversor;
import com.mmontes.util.dto.TIPDetailsDto;
import com.mmontes.util.exception.GeometryParsingException;
import com.mmontes.util.exception.InstanceNotFoundException;
import com.vividsolutions.jts.geom.Geometry;
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

    @Autowired
    private TIPtypeService tiPtypeService;

    @RequestMapping(value = "/admin/tip", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TIPDetailsDto>
    create(@RequestBody TIPRequest tipRequest) {

        if (tipRequest.getName() == null || tipRequest.getName().isEmpty() ||
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
        TIPDetailsDto tipDetailsDto;
        try {
            tipDetailsDto = tipService.create(tipRequest.getType(), tipRequest.getName(), tipRequest.getDescription(),
                    tipRequest.getPhotoUrl(), tipRequest.getInfoUrl(), geometry);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(tipDetailsDto, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/tip/{TIPId}", method = RequestMethod.GET)
    public ResponseEntity<TIPDetailsDto>
    find(@PathVariable Long TIPId) {
        TIPDetailsDto tipDetailsDto;
        try {
            tipDetailsDto = tipService.findById(TIPId);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tipDetailsDto, HttpStatus.OK);
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
        TIPDetailsDto tipDetailsDto;
        try {
            tipDetailsDto = tipService.edit(TIPId, tipPatchRequest);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tipDetailsDto,HttpStatus.OK);
    }

    @RequestMapping(value = "/tip/types", method = RequestMethod.GET)
    public ResponseEntity<List<TIPtype>>
    findAllTIPtypes() {
        List<TIPtype> types = tiPtypeService.findAllTypes();
        return new ResponseEntity<>(types, HttpStatus.OK);
    }

    @RequestMapping(value = "/tip/type/{TIPtypeId}", method = RequestMethod.GET)
    public ResponseEntity
    getTypeName(@PathVariable Long TIPtypeId) {
        String name;
        try {
            name = tiPtypeService.findTypeName(TIPtypeId);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ResponseFactory.getCustomJSON("name",name), HttpStatus.OK);
    }
}