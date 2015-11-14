package com.mmontes.model.service;

import com.amazonaws.util.json.JSONException;
import com.mmontes.util.dto.UserDto;
import com.mmontes.util.exception.FacebookServiceException;

import java.io.IOException;

public interface UserService {

    UserDto createOrRetrieveUser(String FBaccessToken,Long FBID) throws JSONException, IOException, FacebookServiceException;
}
