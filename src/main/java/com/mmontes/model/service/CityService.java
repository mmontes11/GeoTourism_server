package com.mmontes.model.service;

import com.mmontes.model.entity.City;
import com.mmontes.util.dto.IDnameDto;
import com.mmontes.util.dto.CityEnvelopeDto;
import com.mmontes.util.exception.InstanceNotFoundException;
import com.mmontes.util.exception.TIPLocationException;
import com.vividsolutions.jts.geom.Geometry;

import java.util.List;

public interface CityService {

    City getCityFromLocation(Geometry location) throws TIPLocationException;

    boolean isLocatedInExistingCity(Geometry location);

    List<IDnameDto> findAll();

    Geometry getGeomUnionCities(List<Long> cityIds) throws InstanceNotFoundException;

    void syncCities(List<IDnameDto> cities);

    List<CityEnvelopeDto> getCityEnvelopes();
}
