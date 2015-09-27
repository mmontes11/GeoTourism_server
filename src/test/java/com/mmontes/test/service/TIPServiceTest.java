package com.mmontes.test.service;

import com.mmontes.service.internal.TIPService;
import com.mmontes.util.GeometryConversor;
import com.mmontes.util.dto.TIPDto;
import com.mmontes.util.exception.AmazonServiceExeption;
import com.mmontes.util.exception.InvalidTIPUrlException;
import com.mmontes.util.exception.TIPLocationException;
import com.mmontes.util.exception.WikipediaServiceException;
import com.vividsolutions.jts.geom.Geometry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mmontes.test.util.Constants.*;
import static com.mmontes.util.Constants.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class TIPServiceTest {

    @Autowired
    private TIPService tipService;

    @Test
    public void createBasicTIPs() {
        try {
            String name = "Tower of Hercules";
            String description = "Human Patrimony";
            Geometry geom = GeometryConversor.geometryFromGeoJSON(POINT_TORRE_HERCULES_GEOJSON);
            TIPDto tipDto = tipService.create(MONUMENT_DISCRIMINATOR, name, description, VALID_TIP_PHOTO_URL, null, null, null, geom);

            assertEquals(MONUMENT_DISCRIMINATOR, tipDto.getType());
            assertEquals(name, tipDto.getName());
            assertEquals(description, tipDto.getDescription());
            assertNotNull(tipDto.getGeom());
            assertEquals(VALID_TIP_PHOTO_URL, tipDto.getPhotoUrl());
            assertNotNull(tipDto.getInforUrl());
            assertNotNull(tipDto.getGoogleMapsUrl());
            assertEquals(NAME_CITY_A_CORUNA, tipDto.getCity());
            assertEquals(NAME_REGION_GALICIA, tipDto.getRegion());
            assertEquals(NAME_COUNTRY_ESPANA, tipDto.getCountry());

            name = "Alameda Park";
            description = "Green zone";
            geom = GeometryConversor.geometryFromGeoJSON(POINT_ALAMEDA_GEOJSON);
            tipDto = tipService.create(NATURAL_SPACE_DISCRIMINATOR, name, description, VALID_TIP_PHOTO_URL, null, null, null, geom);

            assertEquals(NATURAL_SPACE_DISCRIMINATOR, tipDto.getType());
            assertEquals(name, tipDto.getName());
            assertEquals(description, tipDto.getDescription());
            assertNotNull(tipDto.getGeom());
            assertEquals(VALID_TIP_PHOTO_URL, tipDto.getPhotoUrl());
            assertNotNull(tipDto.getInforUrl());
            assertNotNull(tipDto.getGoogleMapsUrl());
            assertEquals(NAME_CITY_SANTIAGO, tipDto.getCity());
            assertEquals(NAME_REGION_GALICIA, tipDto.getRegion());
            assertEquals(NAME_COUNTRY_ESPANA, tipDto.getCountry());

        } catch (AmazonServiceExeption | TIPLocationException | WikipediaServiceException | InvalidTIPUrlException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void createFindTIP(){
        String name = "Santiago de Compostela cathedral";
        String description = "Human patrimony";
        Geometry geom = GeometryConversor.geometryFromGeoJSON(POINT_CATEDRAL_SANTIAGO_GEOJSON);
        try {
            TIPDto tipDto = tipService.create(MONUMENT_DISCRIMINATOR, name, description, null, null, null, null, geom);
            List<TIPDto> tipDtos = tipService.find(null, geom, MONUMENT_DISCRIMINATOR, null, null, null);

            assertEquals(1,tipDtos.size());
            TIPDto result = tipDtos.get(0);
            assertEquals(tipDto.getId(),result.getId());
            assertEquals(tipDto.getType(),result.getType());
            assertEquals(tipDto.getName(), result.getName());
            assertEquals(tipDto.getDescription(), result.getDescription());
            assertEquals(tipDto.getGeom(), result.getGeom());
            assertEquals(tipDto.getDescription(), result.getDescription());
            assertEquals(tipDto.getPhotoUrl(), result.getPhotoUrl());
            assertEquals(tipDto.getInforUrl(), result.getInforUrl());
            assertEquals(tipDto.getGoogleMapsUrl(), result.getGoogleMapsUrl());
            assertEquals(tipDto.getCity(), result.getCity());
            assertEquals(tipDto.getRegion(), result.getRegion());
            assertEquals(tipDto.getCountry(), result.getCountry());

        } catch (AmazonServiceExeption | InvalidTIPUrlException | WikipediaServiceException | TIPLocationException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test(expected = TIPLocationException.class)
    public void creatTIPinNonCreatedCity() throws TIPLocationException {
        String name = "Liberty Statue";
        String description = "NY symbol";
        Geometry geom = GeometryConversor.geometryFromGeoJSON(POINT_STATUE_OF_LIBERTRY_GEOJSON);
        try {
            tipService.create(MONUMENT_DISCRIMINATOR, name, description, null, null, null, null, geom);
        } catch (AmazonServiceExeption | WikipediaServiceException | InvalidTIPUrlException e) {
            e.printStackTrace();
            fail();
        }
    }

    /*
    @Test
    public void uploadAmazonS3() {
        String name = "Santiago de Compostela cathedral";
        String description = "Human patrimony";
        Geometry geom = GeometryConversor.geometryFromGeoJSON(POINT_CATEDRAL_SANTIAGO_GEOJSON);
        String content = "This is a test file";
        try {
            TIPDto tipDto = tipService.create(MONUMENT_DISCRIMINATOR,name,description,null,content,".txt",null,geom);
        } catch (AmazonServiceExeption | InvalidTIPUrlException | WikipediaServiceException | TIPLocationException e) {
            e.printStackTrace();
            fail();
        }
    }
    */

    @Test
    public void findTipsOfCurrentCity(){
        TIPDto towerHercules = null;
        TIPDto santiagoCathedral = null;
        try {
            String name = "Tower of Hercules";
            String description = "Human Patrimony";
            Geometry geom = GeometryConversor.geometryFromGeoJSON(POINT_TORRE_HERCULES_GEOJSON);
            towerHercules = tipService.create(MONUMENT_DISCRIMINATOR, name, description, VALID_TIP_PHOTO_URL, null, null, null, geom);

            name = "Santiago de Compostela cathedral";
            description = "Human patrimony";
            geom = GeometryConversor.geometryFromGeoJSON(POINT_CATEDRAL_SANTIAGO_GEOJSON);
            santiagoCathedral = tipService.create(NATURAL_SPACE_DISCRIMINATOR, name, description, VALID_TIP_PHOTO_URL, null, null, null, geom);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        Geometry location = GeometryConversor.geometryFromGeoJSON(POINT_ALAMEDA_GEOJSON);
        List<TIPDto> tipDtos = tipService.find(null, location, null, null, null, null);

        assertEquals(1,tipDtos.size());
        assertEquals(santiagoCathedral.getId(),tipDtos.get(0).getId());
    }

    @Test
    public void finTipsFromFarCity(){

    }
}