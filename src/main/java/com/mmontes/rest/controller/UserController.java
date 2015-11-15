package com.mmontes.rest.controller;

import com.amazonaws.util.json.JSONException;
import com.mmontes.model.service.UserAccountService;
import com.mmontes.rest.request.UserRequest;
import com.mmontes.util.dto.UserAccountDto;
import com.mmontes.util.exception.FacebookServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;

@RestController
public class UserController {

    @Autowired
    private UserAccountService userAccountService;

    @RequestMapping(value = "/user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserAccountDto>
    createOrRetrieveUser(@RequestBody UserRequest userRequest) {
        HashMap<String, Object> result;
        try {
            result = userAccountService.createOrRetrieveUser(userRequest.getAccessToken(), userRequest.getUserID());
        } catch (FacebookServiceException | JSONException | IOException e) {
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
