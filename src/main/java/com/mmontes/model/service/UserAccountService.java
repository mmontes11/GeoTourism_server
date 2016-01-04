package com.mmontes.model.service;

import com.amazonaws.util.json.JSONException;
import com.mmontes.util.dto.UserAccountDto;
import com.mmontes.util.exception.FacebookServiceException;
import com.mmontes.util.exception.InstanceNotFoundException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface UserAccountService {

    HashMap<String, Object> createOrRetrieveUser(String FBaccessToken, Long FBuserID) throws JSONException, IOException, FacebookServiceException;
    List<UserAccountDto> getFriends(Long FBuserID) throws InstanceNotFoundException;
    List<Long> getFacebookUserIds(Integer who, Long facebookUserId, List<Long> friendsFacebookUserIds) throws InstanceNotFoundException;
}
