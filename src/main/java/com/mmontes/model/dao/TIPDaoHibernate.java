package com.mmontes.model.dao;

import com.mmontes.model.entity.TIP;
import com.mmontes.model.util.dao.GenericDaoHibernate;
import com.mmontes.util.GeometryConversor;
import com.vividsolutions.jts.geom.Geometry;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("TIPDao")
public class TIPDaoHibernate extends GenericDaoHibernate<TIP, Long> implements TIPDao {

    public List<TIP> find(Geometry bounds, Long typeId, Long cityId, List<Long> facebookUserIds) {

        boolean filterByType = false;
        boolean filterByLocation = false;

        String queryString = "SELECT * FROM tip ";
        if (typeId != null){
            queryString += "WHERE typeid = '"+typeId+"' ";
            filterByType = true;
        }
        if (bounds != null){
            String boundsWKT = GeometryConversor.wktFromGeometry(bounds);
            String filterLocationString = "ST_Intersects(ST_GeographyFromText('"+boundsWKT+"'), ST_GeographyFromText(ST_AsText(geom)))";

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
