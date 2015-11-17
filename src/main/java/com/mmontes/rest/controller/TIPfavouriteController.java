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
    createFavourite(@PathVariable Long TIPId,
                    @RequestParam(value = "facebookUserId", required = true) Long facebookUserId,
                    @RequestBody Object body) {
        try {
            favouriteService.markAsFavourite(TIPId, facebookUserId);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/social/tip/{TIPId}/favourite", method = RequestMethod.DELETE)
    public ResponseEntity
    deleteFavourite(@PathVariable Long TIPId,
                    @RequestParam(value = "facebookUserId", required = true) Long facebookUserId) {
        try {
            favouriteService.deleteFavourite(TIPId, facebookUserId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
