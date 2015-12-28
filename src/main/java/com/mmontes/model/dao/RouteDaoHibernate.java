package com.mmontes.model.dao;

import com.mmontes.model.entity.TIP.TIP;
import com.mmontes.model.entity.route.Route;
import com.mmontes.model.util.genericdao.GenericDaoHibernate;
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
}
