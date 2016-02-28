package com.mmontes.model.dao;

import com.mmontes.util.Constants;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("StatsDao")
@SuppressWarnings("unchecked")
public class StatsDaoHibernate implements StatsDao {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<List<Double>> getBestRated(){
        String queryString =
                "SELECT ST_Y(t.geom),ST_X(t.geom),AVG(r.ratingvalue)/:maxRatingValue AS intensity " +
                        "FROM tip t " +
                        "JOIN rating r " +
                        "ON t.id = r.tipid " +
                        "GROUP BY t.id";
        Query query = getSession().createSQLQuery(queryString).setParameter("maxRatingValue", Constants.MAX_RATING_VALUE);
        query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        List<Map<String,Object>> aliasToValueMapList = query.list();
        return null;
    }
}
