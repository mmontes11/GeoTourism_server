package com.mmontes.model.service;

import com.mmontes.model.entity.City;
import com.vividsolutions.jts.geom.Geometry;

public interface CityService {

    City getCityFromLocation(Geometry location);

    boolean isLocatedInExistingCity(Geometry location);
}
