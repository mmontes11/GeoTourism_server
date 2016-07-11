package com.mmontes.rest.controller;

import com.mmontes.model.service.CityService;
import com.mmontes.model.service.SyncService;
import com.mmontes.model.service.TIPService;
import com.mmontes.util.dto.IDnameDto;
import com.mmontes.util.dto.TIPSyncDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SyncController {

    @Autowired
    private SyncService syncService;

    @Autowired
    private TIPService tipService;

    @Autowired
    private CityService cityService;

    @RequestMapping(value = "/admin/sync", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity
    synchronize() {
        syncService.sync();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/tips/sync", method = RequestMethod.POST)
    public ResponseEntity
    syncTIPs(@RequestBody List<TIPSyncDto> tipSyncDtos){
        tipService.syncTIPs(tipSyncDtos);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/cities/sync", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity
    syncCities(@RequestBody List<IDnameDto> cities) {
        cityService.syncCities(cities);
        return new ResponseEntity(HttpStatus.OK);
    }
}
