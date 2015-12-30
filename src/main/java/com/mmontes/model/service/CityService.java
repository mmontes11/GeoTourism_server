package com.mmontes.model.service;

import com.mmontes.model.entity.City;
import com.mmontes.util.dto.CityDto;
import com.vividsolutions.jts.geom.Geometry;

import java.util.List;

public interface CityService {

    City getCityFromLocation(Geometry location);
    boolean isLocatedInExistingCity(Geometry location);
    List<CityDto> findAll();
}
