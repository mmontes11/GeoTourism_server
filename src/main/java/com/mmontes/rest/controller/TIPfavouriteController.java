package com.mmontes.rest.controller;

import com.mmontes.model.service.FavouriteService;
import com.mmontes.util.exception.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TIPfavouriteController {

    @Autowired
    private FavouriteService favouriteService;

    @RequestMapping(value = "/social/tip/{TIPId}/favourite", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity
    favourite(@PathVariable Long TIPId,
                    @RequestParam(value = "facebookUserId", required = true) Long facebookUserId,
                    @RequestParam(value = "favouriteValue", required = true) boolean favouriteValue) {

        try {
            if (!favouriteService.isFavourite(TIPId,facebookUserId) && favouriteValue){
                favouriteService.markAsFavourite(TIPId, facebookUserId);
                return new ResponseEntity(HttpStatus.CREATED);
            } else {
                if (favouriteService.isFavourite(TIPId,facebookUserId) && !favouriteValue){
                    favouriteService.deleteFavourite(TIPId, facebookUserId);
                    return new ResponseEntity(HttpStatus.OK);
                }
            }
            return new ResponseEntity(HttpStatus.NOT_MODIFIED);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
