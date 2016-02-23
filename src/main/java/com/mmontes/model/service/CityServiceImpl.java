package com.mmontes.model.service;

import com.mmontes.model.dao.CityDao;
import com.mmontes.model.entity.City;
import com.mmontes.service.OpenStreetMapService;
import com.mmontes.util.GeometryUtils;
import com.mmontes.util.dto.CityDto;
import com.mmontes.util.dto.CityEnvelopeDto;
import com.mmontes.util.dto.DtoService;
import com.mmontes.util.exception.InstanceNotFoundException;
import com.vividsolutions.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("cityService")
@Transactional
public class CityServiceImpl implements CityService {

    @Autowired
    private CityDao cityDao;

    @Autowired
    private DtoService dtoService;

    @Autowired
    private OpenStreetMapService OSMservice;


    public City getCityFromLocation(Geometry location) {
        return cityDao.getCityFromLocation(location);
    }

    @Override
    public boolean isLocatedInExistingCity(Geometry location) {
        return (cityDao.getCityFromLocation(location) != null);
    }

    @Override
    public List<CityDto> findAll() {
        return dtoService.ListCity2ListCityDto(cityDao.findAll());
    }

    @Override
    public Geometry getGeomUnionCities(List<Long> cityIds) throws InstanceNotFoundException {
        if (cityIds != null && !cityIds.isEmpty()) {
            List<Geometry> cityGeoms = new ArrayList<>();
            for (Long id : cityIds) {
                City city = cityDao.findById(id);
                cityGeoms.add(city.getGeom());
            }
            return GeometryUtils.apply(cityGeoms, GeometryUtils.GeomOperation.UNION);
        } else {
            return null;
        }
    }

    @Override
    public void syncCities(List<CityDto> cityDtos) {
        for (CityDto cityDto : cityDtos) {
            Long osmId = cityDto.getId();
            City city;
            try {
                city = cityDao.findByOsmId(osmId);
            } catch (InstanceNotFoundException e) {
                try {
                    Geometry cityGeom = OSMservice.getGeometryByOSMId(osmId);
                    city = new City();
                    city.setGeom(cityGeom);
                    city.setOsmId(osmId);
                } catch (Exception osmException) {
                    e.printStackTrace();
                    continue;
                }
            }
            city.setName(cityDto.getName());
            cityDao.save(city);
        }
    }

    @Override
    public List<CityEnvelopeDto> getCityEnvelopes() {
        List<City> cities = cityDao.getCityEnvelopes();
        List<CityEnvelopeDto> cityEnvelopes = new ArrayList<>();
        for (City c : cities) {
            CityEnvelopeDto cityEnvelopeDto = new CityEnvelopeDto();
            cityEnvelopeDto.setId(c.getId());
            cityEnvelopeDto.setGeom(GeometryUtils.getBBoxString(c.getGeom()));
            cityEnvelopes.add(cityEnvelopeDto);
        }
        return cityEnvelopes;
    }
}
