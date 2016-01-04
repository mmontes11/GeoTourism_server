package com.mmontes.model.dao;

import com.mmontes.model.entity.TIP.TIP;
import com.mmontes.model.entity.route.Route;
import com.mmontes.model.util.genericdao.GenericDao;
import com.mmontes.util.exception.InstanceNotFoundException;
import com.vividsolutions.jts.geom.Geometry;

import java.util.List;

public interface RouteDao extends GenericDao<Route, Long> {

    List<TIP> getTIPsInOrder(Long routeId);
    Route getRouteByIDandUser(Long routeId,Long facebookUserId) throws InstanceNotFoundException;
    List<Route> getRoutesByTIP(Long tipId);
    List<Route> find(Geometry bounds, List<String> travelModes, List<Long> cityIds, List<Long> facebookUserIds);
}
