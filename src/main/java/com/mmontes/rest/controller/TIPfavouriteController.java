package com.mmontes.rest.controller;

import com.mmontes.model.service.FavouriteService;
import com.mmontes.util.dto.UserAccountDto;
import com.mmontes.util.exception.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TIPfavouriteController {

    @Autowired
    private FavouriteService favouriteService;

    @RequestMapping(value = "/social/tip/{TIPId}/favourite", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserAccountDto>>
    favourite(@PathVariable Long TIPId,
                    @RequestHeader(value = "FacebookUserId", required = true) Long facebookUserId,
                    @RequestParam(value = "favouriteValue", required = true) boolean favouriteValue) {
        try {
            HttpStatus status = null;
            if (!favouriteService.isFavourite(TIPId,facebookUserId) && favouriteValue){
                favouriteService.markAsFavourite(TIPId, facebookUserId);
                status = HttpStatus.CREATED;
            } else {
                if (favouriteService.isFavourite(TIPId,facebookUserId) && !favouriteValue){
                    favouriteService.deleteFavourite(TIPId, facebookUserId);
                    status = HttpStatus.OK;
                }
            }
            if (status == null){
                status = HttpStatus.NOT_MODIFIED;
            }
            List<UserAccountDto> userAccountDtos = favouriteService.getFavouritedBy(TIPId);
            return new ResponseEntity<>(userAccountDtos,status);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
