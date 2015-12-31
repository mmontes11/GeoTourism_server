package com.mmontes.model.dao;

import com.mmontes.model.entity.TIP.TIP;
import com.mmontes.model.entity.route.Route;
import com.mmontes.model.util.genericdao.GenericDaoHibernate;
import com.mmontes.util.exception.InstanceNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("RouteDao")
public class RouteDaoHibernate extends GenericDaoHibernate<Route,Long> implements RouteDao{
    @Override
    @SuppressWarnings("unchecked")
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
}
