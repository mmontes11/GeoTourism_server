package com.mmontes.rest.controller;

import com.mmontes.rest.response.WikipediaResult;
import com.mmontes.service.LanguageDetectorService;
import com.mmontes.service.WikipediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class WikipediaController {

    @Autowired
    private LanguageDetectorService languageDetectorService;

    @Autowired
    private WikipediaService wikipediaService;

    @RequestMapping(value = "/admin/wikipedia", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<WikipediaResult>>
    getLanguage (@RequestParam(value = "keywords", required = true) String keywords){
        String language;
        List<WikipediaResult> results = new ArrayList<>();
        try {
            language = languageDetectorService.getLanguage(keywords);
        } catch (Exception e) {
            return new ResponseEntity<>(results, HttpStatus.OK);
        }
        try {
            results = wikipediaService.search(language,keywords);
        } catch (Exception e) {
            return new ResponseEntity<>(results, HttpStatus.OK);
        }
        return new ResponseEntity<>(results,HttpStatus.OK);
    }
}
