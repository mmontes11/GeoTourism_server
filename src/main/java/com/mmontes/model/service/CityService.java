package com.mmontes.model.service;

import com.mmontes.model.entity.City;
import com.mmontes.util.dto.CityDto;
import com.mmontes.util.dto.CityEnvelopeDto;
import com.mmontes.util.exception.InstanceNotFoundException;
import com.vividsolutions.jts.geom.Geometry;

import java.util.List;

public interface CityService {

    City getCityFromLocation(Geometry location);

    boolean isLocatedInExistingCity(Geometry location);

    List<CityDto> findAll();

    Geometry getGeomUnionCities(List<Long> cityIds) throws InstanceNotFoundException;

    void syncCities(List<CityDto> cities);

    List<CityEnvelopeDto> getCityEnvelopes();
}
