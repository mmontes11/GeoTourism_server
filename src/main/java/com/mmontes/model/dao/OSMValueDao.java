package com.mmontes.model.dao;

import com.mmontes.model.entity.OSM.OSMValue;
import com.mmontes.model.util.genericdao.GenericDao;

import java.util.List;

public interface OSMValueDao extends GenericDao<OSMValue, Long> {
    List<OSMValue> findOSMValuesByOSMKey(String OSMKey);
}
