package com.mmontes.model.dao;

import com.mmontes.model.entity.TIP;
import com.mmontes.model.entity.UserAccount;
import com.mmontes.model.util.QueryUtils;
import com.mmontes.model.util.genericdao.GenericDaoHibernate;
import com.mmontes.util.GeometryConversor;
import com.vividsolutions.jts.geom.Geometry;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.StringJoiner;

@Repository("TIPDao")
public class TIPDaoHibernate extends GenericDaoHibernate<TIP, Long> implements TIPDao {

    public List<TIP> find(Geometry bounds,  List<Long> typeIds, List<Long> cityIds, List<Long> facebookUserIds) {

        boolean filterByBounds = false;
        boolean filterByType = false;

        String queryString = "SELECT * FROM tip ";
        if (bounds != null){
            String boundsWKT = GeometryConversor.wktFromGeometry(bounds);
            queryString += "WHERE ST_Intersects(ST_GeographyFromText('"+boundsWKT+"'), ST_GeographyFromText(ST_AsText(geom)))";
            filterByBounds= true;
        }
        if (typeIds != null && !typeIds.isEmpty()){
            String types = QueryUtils.getINvalues(typeIds);
            queryString += filterByBounds? "AND typeid IN "+types+" " : "WHERE typeid IN "+types+" ";
            filterByType = true;
        }
        if (cityIds != null && !cityIds.isEmpty()){
            String cities = QueryUtils.getINvalues(cityIds);
            queryString += (filterByBounds || filterByType)? "AND cityid IN "+cities+" " : "WHERE cityid IN "+cities+" ";
        }

        Query query = getSession().createSQLQuery(queryString).addEntity(TIP.class);
        return query.list();
    }
}
