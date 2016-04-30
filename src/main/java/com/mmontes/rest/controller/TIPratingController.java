package com.mmontes.rest.controller;

import com.mmontes.model.service.RatingService;
import com.mmontes.rest.response.ResponseFactory;
import com.mmontes.util.exception.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TIPratingController {

    @Autowired
    private RatingService ratingService;

    @RequestMapping(value = "/social/tip/{TIPId}/rating", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity
    upsertRating(@PathVariable Long TIPId,
                 @RequestHeader(value = "FacebookUserId", required = true) Long facebookUserId,
                 @RequestParam(value = "ratingValue", required = true) Double ratingValue){

        try {
            Double averageRate = ratingService.rate(ratingValue,TIPId,facebookUserId);
            return new ResponseEntity<>(ResponseFactory.getCustomJSON("averageRate",String.valueOf(averageRate)),HttpStatus.OK);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

}
