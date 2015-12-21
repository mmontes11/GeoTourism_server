package com.mmontes.model.dao;

import com.mmontes.model.entity.TIP;
import com.mmontes.model.util.QueryUtils;
import com.mmontes.model.util.genericdao.GenericDaoHibernate;
import com.mmontes.util.GeometryConversor;
import com.vividsolutions.jts.geom.Geometry;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("TIPDao")
@SuppressWarnings("unchecked")
public class TIPDaoHibernate extends GenericDaoHibernate<TIP, Long> implements TIPDao {

    public List<TIP> find(Geometry bounds, List<Long> typeIds, List<Long> cityIds, List<Long> facebookUserIds) {

        boolean filterByFacebookUserIds = facebookUserIds != null && !facebookUserIds.isEmpty();
        boolean filterByBounds = false;
        boolean filterByType = false;
        boolean filterByCity = false;

        String queryString = "SELECT * FROM tip t ";
        if (filterByFacebookUserIds) {
            queryString += "JOIN tipuseraccount tu ON t.id = tu.tipid ";
            queryString += "JOIN useraccount u ON tu.userid = u.id ";
        }

        if (bounds != null) {
            String boundsWKT = GeometryConversor.wktFromGeometry(bounds);
            queryString += "WHERE ST_Intersects(ST_GeographyFromText('" + boundsWKT + "'), ST_GeographyFromText(ST_AsText(t.geom)))";
            filterByBounds = true;
        }
        if (typeIds != null && !typeIds.isEmpty()) {
            String types = QueryUtils.getINvalues(typeIds);
            String partialQuery = "t.typeid IN " + types + " ";
            queryString += (filterByBounds ? "AND " : "WHERE ") + partialQuery;
            filterByType = true;
        }
        if (cityIds != null && !cityIds.isEmpty()) {
            String cities = QueryUtils.getINvalues(cityIds);
            String partialQuery = "t.cityid IN " + cities + " ";
            queryString += ((filterByBounds || filterByType) ? "AND " : "WHERE ") + partialQuery;
            filterByCity = true;
        }
        if (filterByFacebookUserIds) {
            String FBuserIds = QueryUtils.getINvalues(facebookUserIds);
            String partialQuery = "u.facebookuserid IN " + FBuserIds + " ";
            queryString += ((filterByBounds || filterByType || filterByCity) ? "AND " : "WHERE ") + partialQuery;
        }

        Query query = getSession().createSQLQuery(queryString).addEntity(TIP.class);
        return query.list();
    }
}
