package com.mmontes.model.dao;

import com.mmontes.model.util.QueryUtils;
import com.mmontes.util.Constants;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
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

    private List<List<Double>> getResulStats(List<Map<String, Object>> statRows){
        List<List<Double>> stats = new ArrayList<>();
        for (Map<String, Object> row : statRows) {
            List<Double> rowStats = new ArrayList<>();
            rowStats.add((Double) row.get("latitude"));
            rowStats.add((Double) row.get("longitude"));
            rowStats.add(((BigDecimal)row.get("intensity")).doubleValue());
            stats.add(rowStats);
        }
        return stats;
    }

    @Override
    public List<List<Double>> getBestRated() {
        String queryString =
                "SELECT ST_Y(t.geom) AS latitude,ST_X(t.geom) AS longitude,AVG(r.ratingvalue)/:maxRatingValue AS intensity " +
                        "FROM tip t " +
                        "JOIN rating r " +
                        "ON t.id = r.tipid " +
                        "GROUP BY t.id " +
                        "ORDER BY intensity DESC";
        Query query = getSession().createSQLQuery(queryString).setParameter("maxRatingValue", Constants.MAX_RATING_VALUE);
        List<Map<String, Object>> queryResult = QueryUtils.query2MapList(query);
        return getResulStats(queryResult);
    }
}
