package com.mmontes.model.dao;

import com.mmontes.model.entity.OSM.OSMType;
import com.mmontes.model.util.genericdao.GenericDao;
import com.mmontes.util.exception.InstanceNotFoundException;

import java.util.List;

public interface OSMTypeDao extends GenericDao<OSMType, Long> {
    List<OSMType> find(boolean hasTIPtype);
    List<String> findByKey(String OSMKey);
    OSMType findByKeyValue(String OSMKey,String OSMValue) throws InstanceNotFoundException;
}
