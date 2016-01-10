package com.mmontes.rest.controller;

import com.google.maps.model.TravelMode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TravelModesController {

    @RequestMapping(value = "/travelModes", method = RequestMethod.GET)
    public ResponseEntity<List<String>>
    getTravelModes() {
        List<String> travelModes = new ArrayList<String>() {{
            add(TravelMode.WALKING.toUrlValue());
            add(TravelMode.BICYCLING.toUrlValue());
            add(TravelMode.DRIVING.toUrlValue());
        }};
        return new ResponseEntity<>(travelModes, HttpStatus.OK);
    }
}
