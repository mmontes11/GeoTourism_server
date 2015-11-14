package com.mmontes.model.dao;

import com.mmontes.model.entity.User;
import com.mmontes.model.util.genericdao.GenericDao;
import com.mmontes.util.exception.InstanceNotFoundException;

public interface UserDao extends GenericDao<User, Long> {

    User findByFBUserID(Long FBUserID) throws InstanceNotFoundException;
}
