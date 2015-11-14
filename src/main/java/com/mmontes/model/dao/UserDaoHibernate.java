package com.mmontes.model.dao;

import com.mmontes.model.entity.Admin;
import com.mmontes.model.entity.User;
import com.mmontes.model.util.genericdao.GenericDaoHibernate;
import com.mmontes.util.exception.InstanceNotFoundException;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDaoHibernate extends GenericDaoHibernate<User,Long> implements UserDao {
    @Override
    public User findByFBUserID(Long FBUserID) throws InstanceNotFoundException {
        String queryString = "SELECT u FROM User u WHERE u.facebookUserId = :id";
        User user = (User) getSession().createQuery(queryString)
                        .setParameter("id",FBUserID)
                        .uniqueResult();
        if (user == null){
            throw new InstanceNotFoundException(FBUserID, User.class.getName());
        }else{
            return user;
        }
    }
}
