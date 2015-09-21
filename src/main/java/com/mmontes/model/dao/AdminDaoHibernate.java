package com.mmontes.model.dao;

import com.mmontes.model.entity.Admin;
import com.mmontes.model.util.GenericDaoHibernate;
import com.mmontes.util.exception.InstanceNotFoundException;
import org.springframework.stereotype.Repository;

@Repository("adminDao")
public class AdminDaoHibernate extends GenericDaoHibernate<Admin, Long> implements AdminDao {
    public boolean checkPassword(String username, String password) {
        String queryString = "SELECT a FROM Admin a WHERE a.username = :username AND a.password = :password";
        return (getSession()
                .createQuery(queryString)
                .setParameter("username", username)
                .setParameter("password", password)
                .uniqueResult() != null);
    }

    public Admin findByUsername(String username) throws InstanceNotFoundException {
        String queryString = "SELECT a FROM Admin a WHERE a.username = :username";
        Admin admin = (Admin) getSession()
                                .createQuery(queryString)
                                .setParameter("username",username)
                                .uniqueResult();
        if (admin == null){
            throw new InstanceNotFoundException(username, Admin.class.getName());
        }else{
            return admin;
        }
    }
}
