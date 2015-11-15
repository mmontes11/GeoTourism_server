package com.mmontes.model.dao;

import com.mmontes.model.entity.UserAccount;
import com.mmontes.model.util.genericdao.GenericDao;
import com.mmontes.util.exception.InstanceNotFoundException;

public interface UserAccountDao extends GenericDao<UserAccount, Long> {

    UserAccount findByFBUserID(Long FBUserID) throws InstanceNotFoundException;
}
