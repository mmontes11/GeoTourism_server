package com.mmontes.model.dao;

import com.mmontes.model.entity.TIP.TIP;
import com.mmontes.model.entity.route.Route;
import com.mmontes.model.util.QueryUtils;
import com.mmontes.model.util.genericdao.GenericDaoHibernate;
import com.mmontes.util.Constants;
import com.mmontes.util.exception.InstanceNotFoundException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("RouteDao")
@SuppressWarnings("unchecked")
public class RouteDaoHibernate extends GenericDaoHibernate<Route,Long> implements RouteDao{
    @Override
    public List<TIP> getTIPsInOrder(Long routeID) {
        String queryString = "SELECT rt.pk.tip FROM RouteTIP rt WHERE rt.pk.route.id = :id ORDER BY rt.ordination";
        return (List<TIP>) getSession().createQuery(queryString).setParameter("id",routeID).list();
    }

    @Override
    public Route getRouteByIDandUser(Long routeId, Long facebookUserId) throws InstanceNotFoundException {
        String queryString = "SELECT r FROM Route r WHERE r.id = :id AND r.creator.facebookUserId = :facebookUserId";
        Route route = (Route) getSession().createQuery(queryString).setParameter("id",routeId).setParameter("facebookUserId",facebookUserId).uniqueResult();
        if (route == null){
            throw new InstanceNotFoundException(routeId, Route.class.getName());
        }else{
            return route;
        }
    }

    @Override
    public List<Route> getRoutesByTIP(Long tipId) {
        String queryString = "SELECT rt.pk.route FROM RouteTIP rt WHERE rt.pk.tip.id = :id";
        return (List<Route>) getSession().createQuery(queryString).setParameter("id",tipId).list();
    }

    @Override
    public List<Route> find(String boundsWKT, List<String> travelModes, List<Long> facebookUserIds) {

        boolean filterByBounds = false;
        boolean filterByTravelMode = false;
        boolean filterByFacebookUserIds = facebookUserIds != null && !facebookUserIds.isEmpty();

        String queryString = "SELECT * FROM route r ";
        if (filterByFacebookUserIds){
            queryString += "JOIN useraccount u ON r.userid = u.id ";
        }
        if (boundsWKT != null) {
            queryString += "WHERE ST_Intersects(r.geom,ST_GeometryFromText('SRID=" + Constants.SRID + ";" + boundsWKT + "')) ";
            filterByBounds = true;
        }
        if (travelModes != null && !travelModes.isEmpty()) {
            String travelModesIn = QueryUtils.getINvalues(travelModes);
            String partialQuery = "r.travelmode IN " + travelModesIn + " ";
            queryString += (filterByBounds ? "AND " : "WHERE ") + partialQuery;
            filterByTravelMode = true;
        }
        if (filterByFacebookUserIds) {
            String FBuserIds = QueryUtils.getINvalues(facebookUserIds);
            String partialQuery = "u.facebookuserid IN " + FBuserIds + " ";
            queryString += ((filterByBounds || filterByTravelMode) ? "AND " : "WHERE ") + partialQuery;
        }
        Query query = getSession().createSQLQuery(queryString).addEntity(Route.class);
        return query.list();
    }
}
