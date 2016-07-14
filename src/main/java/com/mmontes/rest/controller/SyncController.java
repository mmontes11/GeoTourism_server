package com.mmontes.rest.controller;

import com.mmontes.model.service.SyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SyncController {

    @Autowired
    private SyncService syncService;

    @RequestMapping(value = "/admin/tips/sync", method = RequestMethod.GET)
    public ResponseEntity
    syncTIPs(){
        syncService.syncTIPs();
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/tips/addresses/sync", method = RequestMethod.GET)
    public ResponseEntity
    syncAddresses(){
        syncService.syncAddresses();
        return new ResponseEntity(HttpStatus.OK);
    }
}
