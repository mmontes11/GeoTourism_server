package com.mmontes.rest.controller;

import com.mmontes.rest.response.LanguageDetectorResponse;
import com.mmontes.service.LanguageDetectorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LanguageDetectorController {

    @RequestMapping(value = "/admin/language", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<LanguageDetectorResponse>
    getLanguage (@RequestParam(value = "keywords", required = true) String keywords){
        String language = null;
        try {
            language = LanguageDetectorService.getLanguage(keywords);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new LanguageDetectorResponse(language),HttpStatus.OK);
    }
}
