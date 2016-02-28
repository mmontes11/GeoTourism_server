package com.mmontes.model.dao;

import com.mmontes.model.entity.Stats;
import com.mmontes.util.Constants;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("StatsDao")
@SuppressWarnings("unchecked")
public class StatsDaoHibernate implements StatsDao{

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<Stats> getBestRated() {
        String queryString =
                "SELECT ST_Y(t.geom) AS latitude,ST_X(t.geom) AS longitude,AVG(r.ratingvalue)/"+Constants.MAX_RATING_VALUE+" AS intensity " +
                "FROM tip t " +
                "JOIN rating r " +
                "ON t.id = r.tipid " +
                "GROUP BY t.id";
        Query query = getSession().createSQLQuery(queryString).addEntity(Stats.class);
        return query.list();
    }
}
