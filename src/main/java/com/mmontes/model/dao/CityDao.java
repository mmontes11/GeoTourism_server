package com.mmontes.model.dao;

import com.mmontes.model.entity.City;
import com.mmontes.model.util.genericdao.GenericDao;
import com.mmontes.util.dto.CityDto;
import com.mmontes.util.dto.CityEnvelopeDto;
import com.mmontes.util.exception.InstanceNotFoundException;
import com.mmontes.util.exception.TIPLocationException;
import com.vividsolutions.jts.geom.Geometry;

import java.util.List;

public interface CityDao extends GenericDao<City,Long>{

    List<City> getCitiesFromTIPLocation(Geometry location);
    List<City> findAll();
    City findByOsmId(Long osmId) throws InstanceNotFoundException;
    List<City> getCityEnvelopes();
}
