package com.mmontes.rest.controller;

import com.mmontes.model.service.ConfigService;
import com.mmontes.util.dto.ConfigDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @RequestMapping(value = "/admin/config", method = RequestMethod.GET)
    public ResponseEntity<ConfigDto>
    getConfig(){
        return new ResponseEntity<>(configService.getConfig(), HttpStatus.OK);
    }
}
