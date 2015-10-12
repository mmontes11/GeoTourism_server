package com.mmontes.model.dao;

import com.mmontes.model.entity.TIP;
import com.mmontes.model.util.dao.GenericDaoHibernate;
import com.mmontes.util.GeometryConversor;
import com.vividsolutions.jts.geom.Geometry;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mmontes.util.Constants.*;

@Repository("TIPDao")
public class TIPDaoHibernate extends GenericDaoHibernate<TIP, Long> implements TIPDao {

    public List<TIP> find(Geometry location, String type, Long cityId, List<Long> facebookUserIds, Double radius) {

        boolean filterByType = false;
        boolean filterByLocation = false;

        String queryString = "SELECT * FROM tip ";
        if (type != null){
            queryString += "WHERE type = '"+type+"' ";
            filterByType = true;
        }
        if (location != null){
            String locationWKT = GeometryConversor.wktFromGeometry(location);
            Double r = radius != null ? radius : SEARCH_RADIUS_METRES;
            String filterLocationString = "ST_DWithin(ST_GeographyFromText(ST_AsText(geom)), ST_GeographyFromText('"+locationWKT+"'), "+r+")";

            queryString += filterByType? "AND " + filterLocationString : "WHERE " + filterLocationString;
            queryString += " ";
            filterByLocation= true;
        }

        Query query = getSession().createSQLQuery(queryString).addEntity(TIP.class);
        return query.list();
    }

    public void markAsFavourite(Long TIPId, Long facebookUserId) {

    }
}
