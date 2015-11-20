package com.mmontes.model.service;

import com.mmontes.model.dao.CityDao;
import com.mmontes.model.entity.City;
import com.mmontes.util.dto.CityDto;
import com.mmontes.util.dto.DtoService;
import com.vividsolutions.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("cityService")
@Transactional
public class CityServiceImpl implements CityService{

    @Autowired
    private CityDao cityDao;

    @Autowired
    private DtoService dtoService;


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


}
