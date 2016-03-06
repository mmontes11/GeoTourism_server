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

    private List<List<Double>> getResulStats(List<Map<String, Object>> statRows) {
        List<List<Double>> stats = new ArrayList<>();
        for (Map<String, Object> row : statRows) {
            List<Double> rowStats = new ArrayList<>();
            rowStats.add((Double) row.get("latitude"));
            rowStats.add((Double) row.get("longitude"));
            if (row.get("intensity") != null){
                rowStats.add(((BigDecimal)row.get("intensity")).doubleValue());
            }
            stats.add(rowStats);
        }
        return stats;
    }

    @Override
    public List<List<Double>> getBestRated() {
        String queryString =
                "SELECT ST_Y(t.geom) AS latitude,ST_X(t.geom) AS longitude," +
                        "ROUND(CAST(r.ratingvalue AS DECIMAL)/:maxRatingValue,3)  AS intensity " +
                        "FROM tip t " +
                        "JOIN rating r " +
                        "ON t.id = r.tipid ";
        Query query = getSession().createSQLQuery(queryString).setParameter("maxRatingValue", Constants.MAX_RATING_VALUE);
        List<Map<String, Object>> queryResult = QueryUtils.query2MapList(query);
        return getResulStats(queryResult);
    }

    @Override
    public List<List<Double>> getMostCommented() {
        String queryString =
                "SELECT ST_Y(t.geom) AS latitude,ST_X(t.geom) AS longitude " +
                        "FROM tip t " +
                        "JOIN comment c " +
                        "ON t.id = c.tipid " ;
        Query query = getSession().createSQLQuery(queryString);
        List<Map<String, Object>> queryResult = QueryUtils.query2MapList(query);
        return getResulStats(queryResult);
    }

    @Override
    public List<List<Double>> getMostFavourited() {
        String queryString =
                "SELECT ST_Y(t.geom) AS latitude,ST_X(t.geom) AS longitude " +
                        "FROM tip t " +
                        "JOIN tipuseraccount tu " +
                        "ON t.id = tu.tipid ";
        Query query = getSession().createSQLQuery(queryString);
        List<Map<String, Object>> queryResult = QueryUtils.query2MapList(query);
        return getResulStats(queryResult);
    }
}
