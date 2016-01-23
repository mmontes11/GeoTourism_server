package com.mmontes.rest.controller;

import com.amazonaws.util.json.JSONException;
import com.mmontes.model.entity.UserAccount;
import com.mmontes.model.service.UserAccountService;
import com.mmontes.util.dto.UserAccountDto;
import com.mmontes.util.exception.FacebookServiceException;
import com.mmontes.util.exception.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserAccountService userAccountService;

    @RequestMapping(value = "/social/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserAccountDto>
    createOrRetrieveUser(@RequestHeader(value="AuthorizationFB", required = true) String accessToken,
                         @RequestHeader(value = "FacebookUserId", required = true) Long facebookUserId) {
        HashMap<String, Object> result;
        try {
            result = userAccountService.createOrRetrieveUser(accessToken, facebookUserId);
        } catch (FacebookServiceException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
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

    @RequestMapping(value = "/social/user/friends", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserAccountDto>>
    getFriends(@RequestHeader(value = "FacebookUserId", required = true) Long facebookUserId){
        try {
            List<UserAccountDto> friends = userAccountService.getFriends(facebookUserId);
            return new ResponseEntity<>(friends, HttpStatus.OK);
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
