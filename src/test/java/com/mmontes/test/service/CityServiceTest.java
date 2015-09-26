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

import static com.mmontes.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static com.mmontes.util.GlobalNames.SPRING_CONFIG_FILE;
import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class CityServiceTest {

    @Autowired
    private CityService cityService;

    private static final String POINT_TORRE_HERCULES_GEOJSON = "{\"type\":\"Point\",\"coordinates\":[-8.4048645,43.3855127]}";
    private static final String NAME_CITY_A_CORUNA = "A Coruña";
    private static final String GL_DOMAIN = "gl";
    private static final String POINT_CATEDRAL_SANTIAGO_GEOJSON = "{\"type\":\"Point\",\"coordinates\":[-8.5446412,42.8805962]}";
    private static final String NAME_CITY_SANTIAGO  = "Santiago de Compostela";
    private static final String POINT_STATUE_OF_LIBERTRY_GEOJSON = "{\"type\":\"Point\",\"coordinates\":[-74.0445004,40.6892494]}";

    @Test
    public void getCityFromPoint(){
        Geometry cityGeometry  = GeometryConversor.geometryFromGeoJSON(POINT_TORRE_HERCULES_GEOJSON);
        City city = cityService.getCityFromLocation(cityGeometry);
        assertNotNull(city);
        assertEquals(NAME_CITY_A_CORUNA, city.getName());
        assertEquals(GL_DOMAIN, city.getRegion().getDomain());

        cityGeometry  = GeometryConversor.geometryFromGeoJSON(POINT_CATEDRAL_SANTIAGO_GEOJSON);
        city = cityService.getCityFromLocation(cityGeometry);
        assertNotNull(city);
        assertEquals(NAME_CITY_SANTIAGO, city.getName());
        assertEquals(GL_DOMAIN, city.getRegion().getDomain());

        cityGeometry  = GeometryConversor.geometryFromGeoJSON(POINT_STATUE_OF_LIBERTRY_GEOJSON);
        city = cityService.getCityFromLocation(cityGeometry);
        assertNull(city);
    }
}
