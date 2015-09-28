package com.mmontes.rest.controller;

import com.mmontes.service.internal.TIPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/tip")
public class TIPController {

    @Autowired
    private TIPService tipService;

    @RequestMapping( value = "/", method= RequestMethod.GET)
    public String find(Long facebookUserId, String location, String type, Long cityId, Integer favouritedBy, Double radius){
        return "foo";
    }
}
