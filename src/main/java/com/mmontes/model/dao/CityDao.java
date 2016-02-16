package com.mmontes.model.dao;

import com.mmontes.model.entity.City;
import com.mmontes.model.util.genericdao.GenericDao;
import com.mmontes.util.exception.InstanceNotFoundException;
import com.vividsolutions.jts.geom.Geometry;

import java.util.List;

public interface CityDao extends GenericDao<City,Long>{

    City getCityFromLocation(Geometry location);
    List<City> findAll();
    City findByOsmId(Long osmId) throws InstanceNotFoundException;
}
