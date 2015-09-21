package com.mmontes.service.test;

import static com.mmontes.util.GlobalNames.SPRING_CONFIG_FILE;
import static org.junit.Assert.*;

import com.mmontes.model.dao.AdminDao;
import com.mmontes.model.entity.Admin;
import com.mmontes.service.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE })
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
        assertFalse(adminService.checkPassword("test","other"));
    }
}
