package com.mmontes.model.service;

import com.amazonaws.util.json.JSONException;
import com.mmontes.model.dao.UserAccountDao;
import com.mmontes.model.entity.UserAccount;
import com.mmontes.service.FacebookService;
import com.mmontes.util.dto.DtoService;
import com.mmontes.util.dto.UserAccountDto;
import com.mmontes.util.exception.FacebookServiceException;
import com.mmontes.util.exception.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

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
    @SuppressWarnings("unchecked")
    public HashMap<String, Object> createOrRetrieveUser(String accessToken, Long FBuserID) throws JSONException, IOException, FacebookServiceException {
        HashMap<String, Object> result = new HashMap<>();
        UserAccount userAccount;
        facebookService.setParams(accessToken, FBuserID);
        try {
            userAccount = userDao.findByFBUserID(FBuserID);
            result.put("created", false);
        } catch (InstanceNotFoundException e) {
            userAccount = new UserAccount();
            userAccount.setRegistrationDate(Calendar.getInstance());
            userAccount.setFacebookUserId(FBuserID);
            result.put("created", true);
        }
        HashMap<String,String> details = facebookService.getUserDetails();
        userAccount.setName(details.get("name"));
        userAccount.setFacebookProfileUrl(details.get("link"));
        userAccount.setFacebookProfilePhotoUrl(facebookService.getUserProfilePhoto());
        userAccount.getFriends().clear();
        List<UserAccount> friends = userDao.findUserAccountsByFBUserIDs(facebookService.getUserFriends());
        userAccount.getFriends().addAll(friends);
        userDao.save(userAccount);
        result.put("userAccountDto", dtoService.UserAccount2UserAccountDto(userAccount));
        return result;
    }

    @Override
    public List<UserAccountDto> getFriends(Long FBuserID) throws InstanceNotFoundException {
        UserAccount userAccount = userDao.findByFBUserID(FBuserID);
        List<UserAccount> friends = new ArrayList<>(userAccount.getFriends());
        return dtoService.ListUserAccount2ListUserAccountDto(friends);
    }
}
