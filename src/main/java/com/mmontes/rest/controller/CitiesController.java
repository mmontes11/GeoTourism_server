package com.mmontes.rest.controller;

import com.mmontes.model.service.CityService;
import com.mmontes.util.dto.CityDto;
import com.mmontes.util.exception.SyncException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CitiesController {

    @Autowired
    private CityService cityService;

    @RequestMapping(value = "/cities", method = RequestMethod.GET)
    public ResponseEntity<List<CityDto>>
    findAllCities() {
        List<CityDto> cities = cityService.findAll();
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/cities/sync", method = RequestMethod.POST)
    public ResponseEntity
    syncCities(@RequestBody List<CityDto> cities) {
        try {
            cityService.syncCities(cities);
        } catch (SyncException e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
