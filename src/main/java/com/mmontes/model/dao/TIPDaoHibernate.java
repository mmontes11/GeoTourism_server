package com.mmontes.model.dao;

import com.mmontes.model.entity.TIP;
import com.mmontes.model.util.dao.GenericDaoHibernate;
import com.vividsolutions.jts.geom.Geometry;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mmontes.util.Constants.*;

@Repository("TIPDao")
public class TIPDaoHibernate extends GenericDaoHibernate<TIP, Long> implements TIPDao {

    public List<TIP> find(Geometry location, String type, Long cityId, List<Long> facebookUserIds, Double radius) {

        boolean filterByLocation = false;
        boolean filterByType = false;
        boolean filterByCity= false;
        boolean filterByFacebookUsers = false;

        String queryString = "SELECT tip FROM TIP tip ";
        if (type != null){
            queryString += "WHERE type = :type ";
            filterByType = true;
        }
        if (location != null){
            String filterLocationString = "dwithin(transform(tip.geom,:srid), transform(:location,:srid), :radius) = true ";
            queryString += filterByType? "AND " + filterLocationString : "WHERE " + filterLocationString;
            queryString += " ";
            filterByLocation= true;
        }

        Query query = getSession().createQuery(queryString);
        if (filterByType){
            query.setParameter("type",type);
        }
        if (filterByLocation){
            double r = radius != null ? radius : SEARCH_RADIUS_METRES;
            query.setParameter("srid",SRID_DWITHIN)
                    .setParameter("location", location)
                    .setParameter("radius",r);
        }
        return query.list();
    }

    public void markAsFavourite(Long TIPId, Long facebookUserId) {

    }
}
