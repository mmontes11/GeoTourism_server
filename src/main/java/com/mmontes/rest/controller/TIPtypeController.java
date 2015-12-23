package com.mmontes.rest.controller;

import com.mmontes.model.entity.TIP.TIPtype;
import com.mmontes.model.service.TIPtypeService;
import com.mmontes.rest.response.ResponseFactory;
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
        return new ResponseEntity<>(ResponseFactory.getCustomJSON("name", name), HttpStatus.OK);
    }
}
