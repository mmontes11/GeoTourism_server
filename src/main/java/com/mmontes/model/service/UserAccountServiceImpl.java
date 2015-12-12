package com.mmontes.model.service;

import com.amazonaws.util.json.JSONException;
import com.mmontes.model.dao.UserAccountDao;
import com.mmontes.model.entity.UserAccount;
import com.mmontes.service.FacebookService;
import com.mmontes.util.dto.DtoService;
import com.mmontes.util.exception.FacebookServiceException;
import com.mmontes.util.exception.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

@Service("UserService")
@Transactional
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private UserAccountDao userDao;

    @Autowired
    private DtoService dtoService;

    @Autowired
    private FacebookService facebookService;

    @Override
    public HashMap<String, Object> createOrRetrieveUser(String accessToken, Long userID) throws JSONException, IOException, FacebookServiceException {
        HashMap<String, Object> result = new HashMap<>();
        UserAccount userAccount;
        facebookService.setParams(accessToken, userID);
        try {
            userAccount = userDao.findByFBUserID(userID);
            userAccount.setFacebookProfilePhotoUrl(facebookService.getUserProfilePhoto());
            result.put("created", false);
        } catch (InstanceNotFoundException e) {
            userAccount = new UserAccount();
            userAccount.setRegistrationDate(Calendar.getInstance());
            userAccount.setFacebookUserId(userID);
            HashMap<String,String> details = facebookService.getUserDetails();
            userAccount.setName(details.get("name"));
            userAccount.setFacebookProfileUrl(details.get("link"));
            userAccount.setFacebookProfilePhotoUrl(facebookService.getUserProfilePhoto());
            userDao.save(userAccount);
            result.put("created", true);
        }
        result.put("userAccountDto", dtoService.UserAccount2UserAccountDto(userAccount));
        return result;
    }
}
