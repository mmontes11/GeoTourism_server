package com.mmontes.model.dao;

import com.mmontes.model.entity.TIP.TIP;
import com.mmontes.model.util.genericdao.GenericDao;
import com.mmontes.util.exception.InstanceNotFoundException;
import com.vividsolutions.jts.geom.Geometry;

import java.util.List;

public interface TIPDao extends GenericDao<TIP, Long>{

    List<TIP> find(String boundsWKT, List<Long> typeIds, List<Long> cityIds, List<Long> facebookUserIds, List<Long> routes, boolean reviewed);
    boolean geometryContainsTIPs(Geometry superGeometry, List<Long> tipIds);
    TIP findByOSMId(Long osmId) throws InstanceNotFoundException;
    void deleteNonExistingFromOSMIds (List<Long> osmIds);
}
