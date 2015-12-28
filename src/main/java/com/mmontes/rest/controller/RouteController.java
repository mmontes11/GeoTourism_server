package com.mmontes.rest.controller;

import com.mmontes.util.dto.RouteDetailsDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RouteController {

    @RequestMapping(value = "/social/tip", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RouteDetailsDto>
    create(){
        //Validate that the route contains at least 2 TIPs and no more than 10
        return null;
    }
}
