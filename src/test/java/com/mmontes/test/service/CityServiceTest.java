package com.mmontes.test.service;

import com.mmontes.model.entity.City;
import com.mmontes.model.service.CityService;
import com.mmontes.util.GeometryConversor;
import com.mmontes.util.exception.GeometryParsingException;
import com.vividsolutions.jts.geom.Point;
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
        Point cityGeometry;
        try {
            cityGeometry = (Point) GeometryConversor.geometryFromWKT(POINT_TORRE_HERCULES);
            City city = cityService.getCityFromLocation(cityGeometry);
            assertNotNull(city);
            assertEquals(NAME_CITY_A_CORUNA, city.getName());

            cityGeometry = (Point) GeometryConversor.geometryFromWKT(POINT_CATEDRAL_SANTIAGO);
            city = cityService.getCityFromLocation(cityGeometry);
            assertNotNull(city);
            assertEquals(NAME_CITY_SANTIAGO, city.getName());

            cityGeometry = (Point) GeometryConversor.geometryFromWKT(POINT_STATUE_OF_LIBERTRY);
            city = cityService.getCityFromLocation(cityGeometry);
            assertNull(city);
        } catch (GeometryParsingException e) {
            e.printStackTrace();
            fail();
        }
    }
}
