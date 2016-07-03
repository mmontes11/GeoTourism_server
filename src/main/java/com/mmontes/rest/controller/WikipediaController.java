package com.mmontes.rest.controller;

import com.mmontes.rest.response.WikipediaResult;
import com.mmontes.service.LanguageDetectorService;
import com.mmontes.service.WikipediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @RequestMapping(value = "/wikipedia", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WikipediaResult>>
    getLanguage (@RequestParam(value = "keywords") String keywords,
                 @RequestParam(value = "language", required = false) String lang){
        String language = lang;
        List<WikipediaResult> results = new ArrayList<>();
        if (lang == null || lang.isEmpty()) {
            try {
                language = languageDetectorService.getLanguage(keywords);
            } catch (Exception e) {
                return new ResponseEntity<>(results, HttpStatus.OK);
            }
        }
        try {
            results = wikipediaService.search(language, keywords);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(results, HttpStatus.OK);
        }
        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}
