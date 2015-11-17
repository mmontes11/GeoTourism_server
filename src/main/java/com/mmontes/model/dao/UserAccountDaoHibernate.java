package com.mmontes.model.dao;

import com.mmontes.model.entity.UserAccount;
import com.mmontes.model.util.genericdao.GenericDaoHibernate;
import com.mmontes.util.exception.InstanceNotFoundException;
import org.springframework.stereotype.Repository;

@Repository("UserDao")
public class UserAccountDaoHibernate extends GenericDaoHibernate<UserAccount, Long> implements UserAccountDao {
    @Override
    public UserAccount findByFBUserID(Long FBUserID) throws InstanceNotFoundException {
        String queryString = "SELECT ua FROM UserAccount ua WHERE ua.facebookUserId = :id";
        UserAccount user = (UserAccount) getSession().createQuery(queryString)
                                .setParameter("id", FBUserID)
                                .uniqueResult();
        if (user == null) {
            throw new InstanceNotFoundException(FBUserID, UserAccount.class.getName());
        } else {
            return user;
        }
    }
}
