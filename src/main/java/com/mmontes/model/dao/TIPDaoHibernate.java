package com.mmontes.model.dao;

import com.mmontes.model.entity.TIP.TIP;
import com.mmontes.model.util.QueryUtils;
import com.mmontes.model.util.genericdao.GenericDaoHibernate;
import com.mmontes.util.Constants;
import com.mmontes.util.GeometryUtils;
import com.mmontes.util.PrivateConstants;
import com.mmontes.util.exception.InstanceNotFoundException;
import com.vividsolutions.jts.geom.Geometry;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("TIPDao")
@SuppressWarnings("unchecked")
public class TIPDaoHibernate extends GenericDaoHibernate<TIP, Long> implements TIPDao {

    public List<TIP> find(String boundsWKT, List<Long> typeIds, List<Long> cityIds, List<Long> facebookUserIds, List<Long> routes, boolean reviewed) {

        boolean filterByFacebookUserIds = facebookUserIds != null && !facebookUserIds.isEmpty();
        boolean filterByContainedInRoutes = routes != null && !routes.isEmpty();

        String queryString = "SELECT * FROM tip t ";
        if (filterByFacebookUserIds) {
            queryString += "JOIN favourite f ON t.id = f.tipid ";
            queryString += "JOIN useraccount u ON f.userid = u.id ";
        }
        if (filterByContainedInRoutes){
            String routeIDs = QueryUtils.getINvalues(routes);
            queryString += "JOIN routetip rt ON t.id = rt.tipid AND rt.routeid IN "+routeIDs+" ";
        }

        queryString += "WHERE t.reviewed =  "+reviewed+" ";
        if (boundsWKT != null) {
            queryString += "AND ST_Within(t.geom,ST_GeometryFromText('SRID=" + Constants.SRID + ";" + boundsWKT + "'))";
        }
        if (typeIds != null && !typeIds.isEmpty()) {
            String types = QueryUtils.getINvalues(typeIds);
            queryString += "AND t.typeid IN " + types + " ";
        }
        if (cityIds != null && !cityIds.isEmpty()) {
            String cities = QueryUtils.getINvalues(cityIds);
            queryString += "AND t.cityid IN " + cities + " ";
        }
        if (filterByFacebookUserIds) {
            String FBuserIds = QueryUtils.getINvalues(facebookUserIds);
            queryString += "AND u.facebookuserid IN " + FBuserIds + " ";
        }
        Query query = getSession().createSQLQuery(queryString).addEntity(TIP.class);
        return query.list();
    }

    @Override
    public boolean geometryContainsTIPs(Geometry superGeometry, List<Long> tipIds) {
        String tipIdsIn = QueryUtils.getINvalues(tipIds);
        String superGeometryWKT = GeometryUtils.WKTFromGeometry(superGeometry);
        String queryString =
                "SELECT id FROM tip " +
                        "WHERE id IN " + tipIdsIn + " " +
                        "AND ST_Distance(ST_GeometryFromText('SRID=" + Constants.SRID + ";" + superGeometryWKT + "'),geom) < " + Constants.MIN_DISTANCE;
        return (getSession().createSQLQuery(queryString).list().size() == tipIds.size());
    }

    @Override
    public TIP findByOSMId(Long osmId) throws InstanceNotFoundException {
        String queryString = "SELECT t FROM TIP t WHERE t.osmId IS NOT NULL AND t.osmId = :id";
        TIP t = (TIP) getSession().createQuery(queryString).setParameter("id", osmId).uniqueResult();
        if (t == null) {
            throw new InstanceNotFoundException(osmId, TIP.class.getName());
        } else {
            return t;
        }
    }

    @Override
    public void deleteNonExistingFromOSMIds(List<Long> osmIds) {
        String ids = QueryUtils.getINvalues(osmIds);
        String queryString = "DELETE FROM TIP t WHERE t.osmId IS NOT NULL AND t.osmId NOT IN " + ids;
        getSession().createSQLQuery(queryString).executeUpdate();
    }
}
