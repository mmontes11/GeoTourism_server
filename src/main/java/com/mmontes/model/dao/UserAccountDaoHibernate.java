package com.mmontes.model.dao;

import com.mmontes.model.entity.UserAccount;
import com.mmontes.model.util.QueryUtils;
import com.mmontes.model.util.genericdao.GenericDaoHibernate;
import com.mmontes.util.exception.InstanceNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("UserDao")
public class UserAccountDaoHibernate extends GenericDaoHibernate<UserAccount, Long> implements UserAccountDao {
    @Override
    public UserAccount findByFBUserID(Long FBUserID) throws InstanceNotFoundException {
        if (FBUserID == null) return null;

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

    @Override
    @SuppressWarnings("unchecked")
    public List<UserAccount> findUserAccountsByFBUserIDs(List<Long> FBUserIDs) {
        if (FBUserIDs == null || FBUserIDs.isEmpty()){
            return new ArrayList<>();
        }
        String friendFBIds = QueryUtils.getINvalues(FBUserIDs);
        String queryString = "SELECT ua FROM UserAccount ua WHERE ua.facebookUserId IN "+friendFBIds;
        return (List<UserAccount>) getSession().createQuery(queryString).list();
    }
}
