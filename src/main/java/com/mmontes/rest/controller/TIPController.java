package com.mmontes.rest.controller;

import com.mmontes.service.internal.TIPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tip")
public class TIPController {

    @Autowired
    private TIPService tipService;

    @RequestMapping( value = "", method= RequestMethod.GET)
    public void find(Long facebookUserId, String location, String type, Long cityId, Integer favouritedBy, Double radius){

    }
}
