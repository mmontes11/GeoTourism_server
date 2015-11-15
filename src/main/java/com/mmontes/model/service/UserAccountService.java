package com.mmontes.model.service;

import com.amazonaws.util.json.JSONException;
import com.mmontes.util.exception.FacebookServiceException;

import java.io.IOException;
import java.util.HashMap;

public interface UserAccountService {

    HashMap<String, Object> createOrRetrieveUser(String FBaccessToken, Long userID) throws JSONException, IOException, FacebookServiceException;
}
