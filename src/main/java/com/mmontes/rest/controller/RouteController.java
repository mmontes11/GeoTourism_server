package com.mmontes.rest.controller;

import com.mmontes.model.service.RouteService;
import com.mmontes.rest.request.RouteRequest;
import com.mmontes.service.GoogleMapsService;
import com.mmontes.util.dto.RouteDetailsDto;
import com.mmontes.util.exception.GoogleMapsServiceException;
import com.mmontes.util.exception.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RouteController {

    @Autowired
    private GoogleMapsService googleMapsService;

    @Autowired
    private RouteService routeService;

    @RequestMapping(value = "/social/route", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RouteDetailsDto>
    create(@RequestBody RouteRequest routeRequest,
           @RequestParam(value = "facebookUserId", required = false) Long facebookUserId){

        if (routeRequest.getName() == null || routeRequest.getName().isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (routeRequest.getDescription() == null || routeRequest.getDescription().isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (!googleMapsService.isValidTravelMode(routeRequest.getTravelMode())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if ((routeRequest.getLineStrings() == null || routeRequest.getLineStrings().size() < 2)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (routeRequest.getTipIds() == null || routeRequest.getTipIds().size() < 2){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String name = routeRequest.getName();
        String description = routeRequest.getDescription();
        String travelMode = routeRequest.getTravelMode();
        List<String> lineStrings = routeRequest.getLineStrings();
        List<Long> tipdIds = routeRequest.getTipIds();
        RouteDetailsDto routeDetailsDto;
        System.out.println("tipIds:");
        System.out.println(tipdIds);
        try {
            routeDetailsDto = routeService.createRoute(name,description,travelMode,null,tipdIds, facebookUserId);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (GoogleMapsServiceException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(routeDetailsDto,HttpStatus.CREATED);
    }
}
