package com.mmontes.test.service;

import com.mmontes.model.entity.City;
import com.mmontes.service.internal.CityService;
import com.mmontes.util.GeometryConversor;
import com.vividsolutions.jts.geom.Geometry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static com.mmontes.test.util.Constants.*;
import static com.mmontes.util.Constants.SPRING_CONFIG_FILE;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE})
@Transactional
public class CityServiceTest {

    @Autowired
    private CityService cityService;

    @Test
    public void getCityFromPoint() {
        Geometry cityGeometry = GeometryConversor.geometryFromGeoJSON(POINT_TORRE_HERCULES_GEOJSON);
        City city = cityService.getCityFromLocation(cityGeometry);
        assertNotNull(city);
        assertEquals(NAME_CITY_A_CORUNA, city.getName());
        assertEquals(GL_DOMAIN, city.getRegion().getDomain());

        cityGeometry = GeometryConversor.geometryFromGeoJSON(POINT_CATEDRAL_SANTIAGO_GEOJSON);
        city = cityService.getCityFromLocation(cityGeometry);
        assertNotNull(city);
        assertEquals(NAME_CITY_SANTIAGO, city.getName());
        assertEquals(GL_DOMAIN, city.getRegion().getDomain());

        cityGeometry = GeometryConversor.geometryFromGeoJSON(POINT_STATUE_OF_LIBERTRY_GEOJSON);
        city = cityService.getCityFromLocation(cityGeometry);
        assertNull(city);
    }
}
