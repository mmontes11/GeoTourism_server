package com.mmontes.model.dao;

import com.mmontes.model.entity.OSM.OSMType;
import com.mmontes.model.util.genericdao.GenericDao;
import com.mmontes.util.exception.InstanceNotFoundException;

import java.util.List;

public interface OSMTypeDao extends GenericDao<OSMType, Long> {
    List<OSMType> getOSMTypes();
    OSMType findByOSMvalueId(Long osmValueId) throws InstanceNotFoundException;
}
