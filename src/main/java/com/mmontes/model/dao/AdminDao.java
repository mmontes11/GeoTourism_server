package com.mmontes.model.dao;

import com.mmontes.model.entity.Admin;
import com.mmontes.model.util.GenericDao;
import com.mmontes.util.exception.InstanceNotFoundException;

public interface AdminDao extends GenericDao<Admin, Long> {

    public boolean checkPassword(String username, String password);
    public Admin findByUsername(String username) throws InstanceNotFoundException;

}
