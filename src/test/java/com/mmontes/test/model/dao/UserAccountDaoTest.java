package com.mmontes.test.model.dao;

import com.mmontes.model.dao.UserAccountDao;
import com.mmontes.model.entity.UserAccount;
import com.mmontes.util.exception.InstanceNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;

import static com.mmontes.test.util.Constants.*;
import static com.mmontes.test.util.Constants.SPRING_CONFIG_TEST_FILE;
import static com.mmontes.util.Constants.SPRING_CONFIG_FILE;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE,SPRING_CONFIG_TEST_FILE })
@Transactional
public class UserAccountDaoTest {

    @Autowired
    private UserAccountDao userAccountDao;

    @Test(expected = InstanceNotFoundException.class)
    public void findNonExistingUser() throws InstanceNotFoundException {
        userAccountDao.findByFBUserID(NON_EXISTING_FACEBOOK_USER_ID);
    }

    @Test
    public void createAndFindUser(){
        UserAccount ua = new UserAccount();
        ua.setName("");
        ua.setRegistrationDate(Calendar.getInstance());
        ua.setFacebookProfilePhotoUrl("");
        ua.setFacebookUserId(CREATE_FACEBOOK_USER_ID);
        userAccountDao.save(ua);

        try {
            assertEquals(ua,userAccountDao.findByFBUserID(CREATE_FACEBOOK_USER_ID));
        } catch (InstanceNotFoundException e) {
            fail();
        }
    }
}
