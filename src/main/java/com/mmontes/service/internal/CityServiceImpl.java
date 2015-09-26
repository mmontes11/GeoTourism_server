package com.mmontes.service.internal;

import com.mmontes.model.dao.CityDao;
import com.mmontes.model.entity.City;
import com.vividsolutions.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("cityService")
@Transactional
public class CityServiceImpl implements CityService{

    @Autowired
    private CityDao cityDao;


    public City getCityFromLocation(Geometry location) {
        return cityDao.getCityFromLocation(location);
    }
}
