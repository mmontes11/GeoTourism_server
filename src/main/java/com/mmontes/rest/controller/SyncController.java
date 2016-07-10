package com.mmontes.rest.controller;

import com.mmontes.model.service.SyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SyncController {

    @Autowired
    private SyncService syncService;

    @RequestMapping(value = "/sync", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity
    synchronize() {
        // TODO: Mover a un lugar que requiera permisos de admin. Añadir llamada a este método en la administración.
        syncService.sync();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
