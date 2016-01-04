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
    private UserAccountDao userAccountDao;

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
            userAccount = userAccountDao.findByFBUserID(FBuserID);
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
        List<UserAccount> friends = userAccountDao.findUserAccountsByFBUserIDs(facebookService.getUserFriends());
        userAccount.getFriends().addAll(friends);
        userAccountDao.save(userAccount);
        result.put("userAccountDto", dtoService.UserAccount2UserAccountDto(userAccount));
        return result;
    }

    @Override
    public List<UserAccountDto> getFriends(Long FBuserID) throws InstanceNotFoundException {
        UserAccount userAccount = userAccountDao.findByFBUserID(FBuserID);
        List<UserAccount> friends = new ArrayList<>(userAccount.getFriends());
        return dtoService.ListUserAccount2ListUserAccountDto(friends);
    }

    @Override
    public List<Long> getFacebookUserIds(Integer who, Long facebookUserId, List<Long> friendsFacebookUserIds) throws InstanceNotFoundException {
        List<Long> facebookUserIds = new ArrayList<>();
        if (who != null) {
            if (who == 0) {
                facebookUserIds.add(facebookUserId);
            } else if (who == 1) {
                if (friendsFacebookUserIds != null && !friendsFacebookUserIds.isEmpty()) {
                    facebookUserIds.addAll(friendsFacebookUserIds);
                } else {
                    UserAccount userAccount = userAccountDao.findByFBUserID(facebookUserId);
                    for (UserAccount user : userAccount.getFriends()) {
                        facebookUserIds.add(user.getFacebookUserId());
                    }
                }
            }
        }
        return facebookUserIds;
    }
}
