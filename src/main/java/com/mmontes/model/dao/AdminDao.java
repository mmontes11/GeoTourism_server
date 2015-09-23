package com.mmontes.model.dao;

import com.mmontes.model.entity.Admin;
import com.mmontes.model.util.dao.GenericDao;
import com.mmontes.util.exception.InstanceNotFoundException;

public interface AdminDao extends GenericDao<Admin, Long> {

    boolean checkPassword(String username, String password);
    Admin findByUsername(String username) throws InstanceNotFoundException;
}
