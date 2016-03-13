package com.mmontes.rest.controller;

import com.mmontes.model.entity.TIP.TIPtype;
import com.mmontes.model.service.TIPtypeService;
import com.mmontes.rest.response.ResponseFactory;
import com.mmontes.util.dto.DtoService;
import com.mmontes.util.dto.IDnameDto;
import com.mmontes.util.dto.TIPtypeDetailsDto;
import com.mmontes.util.exception.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TIPtypeController {

    @Autowired
    private TIPtypeService tiPtypeService;

    @Autowired
    private DtoService dtoService;

    @RequestMapping(value = "/tip/types", method = RequestMethod.GET)
    public ResponseEntity<List<IDnameDto>>
    findAllTIPtypes() {
        List<TIPtype> types = tiPtypeService.findAllTypes();
        return new ResponseEntity<>(dtoService.ListTIPtype2IDnameDtos(types), HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/tip/type/{TIPtypeId}", method = RequestMethod.GET)
    public ResponseEntity<TIPtypeDetailsDto>
    findById(@PathVariable Long TIPtypeId) {
        try {
            TIPtypeDetailsDto tiPtypeDetailsDto = tiPtypeService.findById(TIPtypeId);
            return new ResponseEntity<>(tiPtypeDetailsDto, HttpStatus.OK);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/tip/type/{TIPtypeId}/name", method = RequestMethod.GET)
    public ResponseEntity
    getTypeName(@PathVariable Long TIPtypeId) {
        try {
            String name = tiPtypeService.findTypeName(TIPtypeId);
            return new ResponseEntity<>(ResponseFactory.getCustomJSON("name", name), HttpStatus.OK);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
