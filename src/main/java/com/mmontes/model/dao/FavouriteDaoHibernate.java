package com.mmontes.model.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository("FavouriteDao")
public class FavouriteDaoHibernate implements FavouriteDao {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Integer getMaxNumOfFavs() {
        String queryString = "SELECT MAX(num_favs) FROM (SELECT COUNT(*) AS  num_favs FROM tipuseraccount GROUP BY tipid) AS num_favs_tip";
        BigInteger result = (BigInteger)getSession().createSQLQuery(queryString).uniqueResult();
        return (result == null) ? null : result.intValue();
    }
}