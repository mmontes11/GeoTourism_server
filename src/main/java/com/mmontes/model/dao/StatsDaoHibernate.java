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
            rowStats.add(((BigDecimal)row.get("intensity")).doubleValue());
            stats.add(rowStats);
        }
        return stats;
    }

    private int normalizeDenominator(Integer value){
        if (value == null || value == 0) {
            return 1;
        }else{
            return value;
        }
    }

    @Override
    public List<List<Double>> getBestRated() {
        String queryString =
                "SELECT ST_Y(t.geom) AS latitude,ST_X(t.geom) AS longitude," +
                        "ROUND(CAST(AVG(r.ratingvalue) AS DECIMAL),3)/:maxRatingValue  AS intensity " +
                        "FROM tip t " +
                        "JOIN rating r " +
                        "ON t.id = r.tipid " +
                        "GROUP BY t.id " +
                        "ORDER BY intensity DESC";
        Query query = getSession().createSQLQuery(queryString).setParameter("maxRatingValue", Constants.MAX_RATING_VALUE);
        List<Map<String, Object>> queryResult = QueryUtils.query2MapList(query);
        return getResulStats(queryResult);
    }

    @Override
    public List<List<Double>> getMostCommented(Integer maxNumOfComments) {
        String queryString =
                "SELECT ST_Y(t.geom) AS latitude,ST_X(t.geom) AS longitude," +
                        "ROUND(CAST(COUNT(t.id) AS DECIMAL)/:maxNumOfComments,3) AS intensity " +
                        "FROM tip t " +
                        "JOIN comment c " +
                        "ON t.id = c.tipid " +
                        "GROUP BY t.id " +
                        "ORDER BY intensity DESC";
        if (maxNumOfComments == null || maxNumOfComments == 0) {
            maxNumOfComments = 1;
        }
        maxNumOfComments = normalizeDenominator(maxNumOfComments);
        Query query = getSession().createSQLQuery(queryString).setParameter("maxNumOfComments", maxNumOfComments);
        List<Map<String, Object>> queryResult = QueryUtils.query2MapList(query);
        return getResulStats(queryResult);
    }

    @Override
    public List<List<Double>> getMostFavourited(Integer maxNumOfFavs) {
        String queryString =
                "SELECT ST_Y(t.geom) AS latitude,ST_X(t.geom) AS longitude," +
                        "ROUND(CAST(COUNT(t.id) AS DECIMAL),3)/:maxNumOfFavs AS intensity " +
                        "FROM tip t " +
                        "JOIN tipuseraccount tu " +
                        "ON t.id = tu.tipid " +
                        "GROUP BY t.id " +
                        "ORDER BY intensity DESC";
        maxNumOfFavs = normalizeDenominator(maxNumOfFavs);
        Query query = getSession().createSQLQuery(queryString).setParameter("maxNumOfFavs", maxNumOfFavs);
        List<Map<String, Object>> queryResult = QueryUtils.query2MapList(query);
        return getResulStats(queryResult);
    }
}
