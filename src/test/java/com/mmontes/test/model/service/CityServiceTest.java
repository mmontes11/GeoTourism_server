package com.mmontes.test.model.service;

import com.mmontes.model.entity.City;
import com.mmontes.model.service.CityService;
import com.mmontes.util.GeometryUtils;
import com.mmontes.util.dto.CityEnvelopeDto;
import com.mmontes.util.exception.GeometryParsingException;
import com.mmontes.util.exception.InstanceNotFoundException;
import com.vividsolutions.jts.geom.Point;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
            cityGeometry = (Point) GeometryUtils.geometryFromWKT(POINT_TORRE_HERCULES);
            City city = cityService.getCityFromLocation(cityGeometry);
            assertNotNull(city);
            assertEquals(NAME_CITY_A_CORUNA, city.getName());

            cityGeometry = (Point) GeometryUtils.geometryFromWKT(POINT_CATEDRAL_SANTIAGO);
            city = cityService.getCityFromLocation(cityGeometry);
            assertNotNull(city);
            assertEquals(NAME_CITY_SANTIAGO, city.getName());

            cityGeometry = (Point) GeometryUtils.geometryFromWKT(POINT_ALHAMBRA);
            city = cityService.getCityFromLocation(cityGeometry);
            assertNull(city);
        } catch (GeometryParsingException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void getAllCities() {
        assertEquals(4, cityService.findAll().size());
    }

    @Test
    public void getCityGeomsWKT() {
        try {
            List<Long> cities = new ArrayList<Long>() {{
                add(A_CORUNA_ID);
                add(SANTIAGO_ID);
            }};
            assertNotNull(cityService.getGeomUnionCities(cities));
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void getCityEnvelopes(){
        List<CityEnvelopeDto> cityEnvelopeDtos = cityService.getCityEnvelopes();
        assertNotNull(cityEnvelopeDtos);
        assertEquals(4,cityEnvelopeDtos.size());
    }

}
