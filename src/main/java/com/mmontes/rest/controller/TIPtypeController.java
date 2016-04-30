package com.mmontes.rest.controller;

import com.mmontes.model.entity.OSM.OSMType;
import com.mmontes.model.entity.TIP.TIPtype;
import com.mmontes.model.service.ConfigService;
import com.mmontes.model.service.TIPtypeService;
import com.mmontes.rest.request.TIPtypeRequest;
import com.mmontes.rest.response.ResponseFactory;
import com.mmontes.service.GCMService;
import com.mmontes.util.KeyValue;
import com.mmontes.util.dto.DtoService;
import com.mmontes.util.dto.TIPtypeDto;
import com.mmontes.util.exception.DuplicateInstanceException;
import com.mmontes.util.exception.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TIPtypeController {

    @Autowired
    private TIPtypeService tipTypeService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private DtoService dtoService;

    @Autowired
    private GCMService gcmService;

    private List<OSMType> ListKeyValue2ListOSMType(List<KeyValue> keyValueList) throws InstanceNotFoundException {
        List<OSMType> osmTypes = new ArrayList<>();
        for (KeyValue keyValue : keyValueList) {
            OSMType osmType = configService.getOSMTypeByKeyValue(keyValue.getKey(), keyValue.getValue());
            osmTypes.add(osmType);
        }
        return osmTypes;
    }

    @RequestMapping(value = "/tip/types", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TIPtypeDto>>
    findAllTIPtypes() {
        List<TIPtype> types = tipTypeService.findAllTypes();
        return new ResponseEntity<>(dtoService.ListTIPtype2TIPtypeDto(types), HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/tip/type/{TIPtypeId}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TIPtypeDto>
    findById(@PathVariable Long TIPtypeId) {
        try {
            TIPtypeDto tiPtypeDetailsDto = tipTypeService.findById(TIPtypeId);
            return new ResponseEntity<>(tiPtypeDetailsDto, HttpStatus.OK);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/tip/type/{TIPtypeId}/name", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity
    getTypeName(@PathVariable Long TIPtypeId) {
        try {
            String name = tipTypeService.findTypeName(TIPtypeId);
            return new ResponseEntity<>(ResponseFactory.getCustomJSON("name", name), HttpStatus.OK);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/admin/tip/type", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TIPtypeDto>
    create(@RequestBody TIPtypeRequest tipTypeRequest) {
        if (tipTypeRequest.getName() == null || tipTypeRequest.getName().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            List<OSMType> osmTypes = ListKeyValue2ListOSMType(tipTypeRequest.getOsmTypes());
            TIPtypeDto tipTypeDetailsDto = tipTypeService.create(tipTypeRequest.getName(), tipTypeRequest.getIcon(), osmTypes);
            gcmService.sendMessageTypesUpdated();
            return new ResponseEntity<>(tipTypeDetailsDto, HttpStatus.CREATED);
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/admin/tip/type/{TIPtypeID}", method = RequestMethod.PUT,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TIPtypeDto>
    update(@PathVariable Long TIPtypeID, @RequestBody TIPtypeRequest tipTypeRequest) {
        if (TIPtypeID == null || tipTypeRequest.getName() == null || tipTypeRequest.getName().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            List<OSMType> osmTypes = ListKeyValue2ListOSMType(tipTypeRequest.getOsmTypes());
            tipTypeService.update(TIPtypeID, tipTypeRequest.getName(), tipTypeRequest.getIcon(), osmTypes);
            gcmService.sendMessageTypesUpdated();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/admin/tip/type/{TIPtypeID}", method = RequestMethod.DELETE)
    public ResponseEntity
    delete(@PathVariable Long TIPtypeID) {
        try {
            tipTypeService.delete(TIPtypeID);
            gcmService.sendMessageTypesUpdated();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
