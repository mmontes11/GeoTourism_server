package com.mmontes.model.service;

import com.amazonaws.util.json.JSONException;
import com.mmontes.model.dao.UserDao;
import com.mmontes.model.entity.User;
import com.mmontes.service.FacebookService;
import com.mmontes.util.dto.DtoConversor;
import com.mmontes.util.dto.UserDto;
import com.mmontes.util.exception.FacebookServiceException;
import com.mmontes.util.exception.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDto createOrRetrieveUser(String FBaccessToken,Long FBID) throws JSONException, IOException, FacebookServiceException {
        User user;
        try {
            user = userDao.findByFBUserID(FBID);
        } catch (InstanceNotFoundException e) {
            FacebookService fs = new FacebookService(FBaccessToken,FBID);
            HashMap<String,String> userFB = fs.getUser();

            user = new User();
            user.setRegistrationDate(Calendar.getInstance());
            user.setFacebookUserId(FBID);
            user.setName(userFB.get("name"));
            user.setFacebookProfilePhotoUrl("facebookProfilePhotoUrl");
            userDao.save(user);
        }
        return DtoConversor.User2UserDto(user);
    }
}
