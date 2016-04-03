package com.mmontes.rest.controller;

import com.mmontes.model.entity.TIP.TIPtype;
import com.mmontes.model.service.TIPtypeService;
import com.mmontes.rest.request.TIPtypeRequest;
import com.mmontes.rest.response.ResponseFactory;
import com.mmontes.service.GCMService;
import com.mmontes.util.dto.DtoService;
import com.mmontes.util.dto.IDnameDto;
import com.mmontes.util.dto.TIPtypeDto;
import com.mmontes.util.exception.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TIPtypeController {

    @Autowired
    private TIPtypeService tipTypeService;

    @Autowired
    private DtoService dtoService;

    @Autowired
    private GCMService gcmService;


    @RequestMapping(value = "/tip/types", method = RequestMethod.GET)
    public ResponseEntity<List<TIPtypeDto>>
    findAllTIPtypes() {
        List<TIPtype> types = tipTypeService.findAllTypes();
        return new ResponseEntity<>(dtoService.ListTIPtype2TIPtypeDto(types), HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/tip/type/{TIPtypeId}", method = RequestMethod.GET)
    public ResponseEntity<TIPtypeDto>
    findById(@PathVariable Long TIPtypeId) {
        try {
            TIPtypeDto tiPtypeDetailsDto = tipTypeService.findById(TIPtypeId);
            return new ResponseEntity<>(tiPtypeDetailsDto, HttpStatus.OK);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/tip/type/{TIPtypeId}/name", method = RequestMethod.GET)
    public ResponseEntity
    getTypeName(@PathVariable Long TIPtypeId) {
        try {
            String name = tipTypeService.findTypeName(TIPtypeId);
            return new ResponseEntity<>(ResponseFactory.getCustomJSON("name", name), HttpStatus.OK);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/admin/tip/type", method = RequestMethod.POST)
    public ResponseEntity<TIPtypeDto>
    create(@RequestBody TIPtypeRequest tipTypeRequest) {
        if (tipTypeRequest.getName() == null || tipTypeRequest.getName().isEmpty() ||
                tipTypeRequest.getIcon() == null || tipTypeRequest.getIcon().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            TIPtypeDto tiPtypeDetailsDto = tipTypeService.create(tipTypeRequest.getName(), tipTypeRequest.getIcon(), tipTypeRequest.getOsmKey(), tipTypeRequest.getOsmValue());
            gcmService.sendMessageTypesUpdated();
            gcmService.sendMessageGlobal("New type of Place: " + tiPtypeDetailsDto.getName());
            return new ResponseEntity<>(tiPtypeDetailsDto, HttpStatus.CREATED);
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/admin/tip/type/{TIPtypeID}", method = RequestMethod.PUT)
    public ResponseEntity<TIPtypeDto>
    update(@PathVariable Long TIPtypeID, @RequestBody TIPtypeRequest tipTypeRequest) {
        if (TIPtypeID == null ||tipTypeRequest.getName() == null || tipTypeRequest.getName().isEmpty() ||
                tipTypeRequest.getIcon() == null || tipTypeRequest.getIcon().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            tipTypeService.update(TIPtypeID,tipTypeRequest.getName(),tipTypeRequest.getIcon(),tipTypeRequest.getOsmKey(),tipTypeRequest.getOsmValue());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/admin/tip/type/{TIPtypeID}", method = RequestMethod.DELETE)
    public ResponseEntity
    delete(@PathVariable Long TIPtypeID) {
        if (TIPtypeID == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            tipTypeService.delete(TIPtypeID);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
