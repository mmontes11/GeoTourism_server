package com.mmontes.model.dao;

import com.mmontes.model.entity.OSM.OSMType;
import com.mmontes.model.util.genericdao.GenericDao;

import java.util.List;

public interface OSMTypeDao extends GenericDao<OSMType, Long> {
    List<OSMType> getOSMTypes(Boolean tipTypeSetted);
}
