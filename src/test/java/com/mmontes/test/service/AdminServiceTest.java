package com.mmontes.test.service;

import com.mmontes.model.dao.AdminDao;
import com.mmontes.model.entity.Admin;
import com.mmontes.model.service.internal.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static com.mmontes.util.Constants.SPRING_CONFIG_FILE;
import static com.mmontes.test.util.Constants.SPRING_CONFIG_TEST_FILE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE,SPRING_CONFIG_TEST_FILE })
@Transactional
public class AdminServiceTest {

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private AdminService adminService;

    @Test
    public void testCheckPassword(){
        Admin admin = new Admin("test", "test");
        adminDao.save(admin);
        assertTrue(adminService.checkPassword("test", "test"));
        assertFalse(adminService.checkPassword("test", "other"));
    }
}
