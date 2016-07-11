package com.mmontes.rest.controller;

import com.mmontes.model.service.CityService;
import com.mmontes.rest.response.CityEnvelopeResponse;
import com.mmontes.util.dto.IDnameDto;
import com.mmontes.util.dto.CityEnvelopeDto;
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
public class CitiesController {

    @Autowired
    private CityService cityService;

    @RequestMapping(value = "/cities", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<IDnameDto>>
    findAllCities() {
        List<IDnameDto> cities = cityService.findAll();
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/cities/envelopes", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CityEnvelopeResponse>
    getCityEnvelopes() {
        List<CityEnvelopeDto> cityEnvelopeDtos = cityService.getCityEnvelopes();
        return new ResponseEntity<>(new CityEnvelopeResponse(cityEnvelopeDtos), HttpStatus.OK);
    }

}
