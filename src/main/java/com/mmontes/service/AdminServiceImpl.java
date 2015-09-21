package com.mmontes.service;

import com.mmontes.model.dao.AdminDao;
import com.mmontes.model.entity.Admin;
import com.mmontes.util.exception.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("adminService")
@Transactional
public class AdminServiceImpl implements AdminService{

    @Autowired
    private AdminDao adminDao;

    public boolean checkPassword(String username, String password) {
        try {
            Admin admin = adminDao.findByUsername(username);
            return adminDao.checkPassword(username,password);
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
