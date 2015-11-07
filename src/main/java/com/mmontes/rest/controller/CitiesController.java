package com.mmontes.rest.controller;

import com.mmontes.model.service.CityService;
import com.mmontes.util.dto.CityDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        return new ResponseEntity<>(cities, HttpStatus.MULTI_STATUS.OK);
    }
}
