package com.mmontes.rest.controller;

import com.amazonaws.util.json.JSONException;
import com.mmontes.model.service.UserAccountService;
import com.mmontes.util.dto.UserAccountDto;
import com.mmontes.util.exception.FacebookServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;

@RestController
public class UserController {

    @Autowired
    private UserAccountService userAccountService;

    @RequestMapping(value = "/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserAccountDto>
    createOrRetrieveUser(@RequestHeader(value="AuthorizationFB") String accessToken,
                         @RequestParam(value = "facebookUserId", required = true) Long facebookUserId) {
        HashMap<String, Object> result;
        try {
            result = userAccountService.createOrRetrieveUser(accessToken, facebookUserId);
        } catch (FacebookServiceException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }catch(JSONException | IOException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if ((Boolean) result.get("created")) {
            return new ResponseEntity<>((UserAccountDto) result.get("userAccountDto"), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>((UserAccountDto) result.get("userAccountDto"), HttpStatus.OK);
        }
    }
}
