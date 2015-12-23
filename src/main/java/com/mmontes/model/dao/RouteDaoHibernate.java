package com.mmontes.model.dao;

import com.mmontes.model.entity.route.Route;
import com.mmontes.model.util.genericdao.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

@Repository("RouteDao")
public class RouteDaoHibernate extends GenericDaoHibernate<Route,Long> implements RouteDao{
}
