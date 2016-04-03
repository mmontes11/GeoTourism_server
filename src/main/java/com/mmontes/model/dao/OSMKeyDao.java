package com.mmontes.model.dao;

import com.mmontes.model.entity.OSM.OSMKey;
import com.mmontes.model.util.genericdao.GenericDao;

import java.util.List;

public interface OSMKeyDao extends GenericDao<OSMKey,Long>{

    List<String> findAll();
}
