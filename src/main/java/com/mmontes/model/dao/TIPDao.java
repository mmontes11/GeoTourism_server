package com.mmontes.model.dao;

import com.mmontes.model.entity.TIP.TIP;
import com.mmontes.model.util.genericdao.GenericDao;
import com.vividsolutions.jts.geom.Geometry;

import java.util.List;

public interface TIPDao extends GenericDao<TIP, Long>{

    List<TIP> find(Geometry bounds, List<Long> typeIds, List<Long> cityIds,List<Long> facebookUserIds);
}
