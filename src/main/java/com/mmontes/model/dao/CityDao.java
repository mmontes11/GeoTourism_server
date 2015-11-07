package com.mmontes.model.dao;

import com.mmontes.model.entity.City;
import com.mmontes.model.util.genericdao.GenericDao;
import com.vividsolutions.jts.geom.Geometry;

public interface CityDao extends GenericDao<City,Long>{

    City getCityFromLocation(Geometry location);
}
