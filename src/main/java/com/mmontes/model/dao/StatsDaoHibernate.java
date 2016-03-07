package com.mmontes.model.dao;

import com.mmontes.model.util.QueryUtils;
import com.mmontes.util.Constants;
import com.mmontes.util.dto.LatLngWeight;
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

    private List<LatLngWeight> getResulStats(List<Map<String, Object>> statRows) {
        List<LatLngWeight> stats = new ArrayList<>();
        for (Map<String, Object> row : statRows) {
            Double latitude = (Double) row.get("latitude");
            Double longitude = (Double) row.get("longitude");
            Double weight = ((BigDecimal) row.get("weight")).doubleValue();
            stats.add(new LatLngWeight(latitude, longitude, weight));
        }
        return stats;
    }

    @Override
    public List<LatLngWeight> getBestRated() {
        String queryString =
                "SELECT ST_Y(t.geom) AS latitude,ST_X(t.geom) AS longitude,CAST(AVG(r.ratingvalue) AS DECIMAL) AS weight " +
                        "FROM tip t " +
                        "JOIN rating r " +
                        "ON t.id = r.tipid " +
                        "GROUP BY t.id ";
        Query query = getSession().createSQLQuery(queryString);
        List<Map<String, Object>> queryResult = QueryUtils.query2MapList(query);
        return getResulStats(queryResult);
    }

    @Override
    public List<LatLngWeight> getMostCommented() {
        String queryString =
                "SELECT ST_Y(t.geom) AS latitude,ST_X(t.geom) AS longitude, CAST(COUNT(c.tipid) AS DECIMAL) AS weight " +
                        "FROM tip t " +
                        "JOIN comment c " +
                        "ON t.id = c.tipid " +
                        "GROUP BY t.id ";
        Query query = getSession().createSQLQuery(queryString);
        List<Map<String, Object>> queryResult = QueryUtils.query2MapList(query);
        return getResulStats(queryResult);
    }

    @Override
    public List<LatLngWeight> getMostFavourited() {
        String queryString =
                "SELECT ST_Y(t.geom) AS latitude,ST_X(t.geom) AS longitude, CAST(COUNT(tu.tipid) AS DECIMAL) AS weight " +
                        "FROM tip t " +
                        "JOIN tipuseraccount tu " +
                        "ON t.id = tu.tipid " +
                        "GROUP BY t.id ";
        Query query = getSession().createSQLQuery(queryString);
        List<Map<String, Object>> queryResult = QueryUtils.query2MapList(query);
        return getResulStats(queryResult);
    }
}
