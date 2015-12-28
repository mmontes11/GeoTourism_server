package com.mmontes.model.dao;

import com.mmontes.model.entity.TIP.TIP;
import com.mmontes.model.entity.route.Route;
import com.mmontes.model.util.genericdao.GenericDao;

import java.util.List;

public interface RouteDao extends GenericDao<Route, Long> {

    List<TIP> getTIPsInOrder(Long routeID);
}
