package com.mmontes.test.model.service;

import com.mmontes.model.dao.TIPDao;
import com.mmontes.model.service.TIPService;
import com.mmontes.model.service.TIPtypeService;
import com.mmontes.util.GeometryConversor;
import com.mmontes.util.dto.TIPDetailsDto;
import com.mmontes.util.dto.TIPSearchDto;
import com.mmontes.util.exception.*;
import com.vividsolutions.jts.geom.Geometry;
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
public class TIPServiceTest {

    @Autowired
    private TIPDao tipDao;

    @Autowired
    private TIPService tipService;

    @Autowired
    private TIPtypeService tipTypeService;

    @Test
    public void createBasicTIPs() {
        try {
            String name = "Tower of Hercules";
            String description = "Human Patrimony";
            Point geom = (Point) GeometryConversor.geometryFromWKT(POINT_TORRE_HERCULES);
            TIPDetailsDto tipDetailsDto = tipService.create(MONUMENT_DISCRIMINATOR, name, description, VALID_TIP_PHOTO_URL, VALID_TIP_INFO_URL, geom);

            assertEquals(MONUMENT_DISCRIMINATOR, tipDetailsDto.getType());
            assertEquals(name, tipDetailsDto.getName());
            assertEquals(description, tipDetailsDto.getDescription());
            assertNotNull(tipDetailsDto.getGeom());
            assertEquals(VALID_TIP_PHOTO_URL, tipDetailsDto.getPhotoUrl());
            assertEquals(VALID_TIP_INFO_URL, tipDetailsDto.getInfoUrl());
            assertNotNull(tipDetailsDto.getGoogleMapsUrl());

            name = "Alameda Park";
            description = "Green zone";
            geom = (Point) GeometryConversor.geometryFromWKT(POINT_ALAMEDA);
            tipDetailsDto = tipService.create(NATURAL_SPACE_DISCRIMINATOR, name, description, VALID_TIP_PHOTO_URL, VALID_TIP_INFO_URL, geom);

            assertEquals(NATURAL_SPACE_DISCRIMINATOR, tipDetailsDto.getType());
            assertEquals(name, tipDetailsDto.getName());
            assertEquals(description, tipDetailsDto.getDescription());
            assertNotNull(tipDetailsDto.getGeom());
            assertEquals(VALID_TIP_PHOTO_URL, tipDetailsDto.getPhotoUrl());
            assertEquals(VALID_TIP_INFO_URL, tipDetailsDto.getInfoUrl());
            assertNotNull(tipDetailsDto.getGoogleMapsUrl());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void createFindTIP() {
        String name = "Santiago de Compostela cathedral";
        String description = "Human patrimony";
        try {
            Point geom = (Point) GeometryConversor.geometryFromWKT(POINT_CATEDRAL_SANTIAGO);
            TIPDetailsDto tipDetailsDto = tipService.create(MONUMENT_DISCRIMINATOR, name, description, null, null, geom);
            ArrayList<Long> typeIds = new ArrayList<Long>() {{
                add(MONUMENT_DISCRIMINATOR);
            }};
            List<TIPSearchDto> tipSearchDtos = tipService.find(null, typeIds, null, null, null, null);

            assertEquals(1, tipSearchDtos.size());
            TIPSearchDto result = tipSearchDtos.get(0);
            assertEquals(tipDetailsDto.getId(), result.getId());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test(expected = TIPLocationException.class)
    public void creatTIPinNonCreatedCity() throws TIPLocationException {
        String name = "Alhambra";
        String description = "Patrimonio de la humanidad";
        try {
            Point geom = (Point) GeometryConversor.geometryFromWKT(POINT_ALHAMBRA);
            tipService.create(MONUMENT_DISCRIMINATOR, name, description, null, null, geom);
        } catch (InvalidTIPUrlException | GeometryParsingException | InstanceNotFoundException | GoogleMapsServiceException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void findTipsOfCurrentCity() {
        TIPDetailsDto santiagoCathedral = null;
        Geometry bounds = null;
        try {
            String name = "Tower of Hercules";
            String description = "Human Patrimony";
            Point geom = (Point) GeometryConversor.geometryFromWKT(POINT_TORRE_HERCULES);
            tipService.create(MONUMENT_DISCRIMINATOR, name, description, VALID_TIP_PHOTO_URL, null, geom);

            name = "Santiago de Compostela cathedral";
            description = "Human Patrimony";
            geom = (Point) GeometryConversor.geometryFromWKT(POINT_CATEDRAL_SANTIAGO);
            santiagoCathedral = tipService.create(MONUMENT_DISCRIMINATOR, name, description, VALID_TIP_PHOTO_URL, null, geom);
            bounds = GeometryConversor.geometryFromWKT(BOUNDS_SANTIAGO_DE_COMPOSTELA);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        List<TIPSearchDto> tipSearchDtos = null;
        try {
            tipSearchDtos = tipService.find(bounds, null, null, null, null, null);
        } catch (InstanceNotFoundException e) {
            fail();
        }

        assertEquals(1, tipSearchDtos.size());
        assertEquals(santiagoCathedral.getId(), tipSearchDtos.get(0).getId());
    }

    @Test
    public void finTipsFromFarCity() {
        Geometry bounds = null;
        try {
            String name = "Tower of Hercules";
            String description = "Human Patrimony";
            Point geom = (Point) GeometryConversor.geometryFromWKT(POINT_TORRE_HERCULES);
            tipService.create(MONUMENT_DISCRIMINATOR, name, description, VALID_TIP_PHOTO_URL, null, geom);

            name = "Alameda Santiago de Compostela";
            description = "Sitio verde";
            geom = (Point) GeometryConversor.geometryFromWKT(POINT_ALAMEDA);
            tipService.create(NATURAL_SPACE_DISCRIMINATOR, name, description, VALID_TIP_PHOTO_URL, null, geom);

            bounds = GeometryConversor.geometryFromWKT(BOUNDS_NEW_YORK);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        List<TIPSearchDto> tipSearchDtos = null;
        try {
            tipSearchDtos = tipService.find(bounds, null, null, null, null, null);
        } catch (InstanceNotFoundException e) {
            fail();
        }

        assertTrue(tipSearchDtos.isEmpty());
    }

    @Test
    public void findTipsByTypes() {
        TIPDetailsDto towerHercules ;
        try {
            String name = "Tower of Hercules";
            String description = "Human Patrimony";
            Point geom = (Point) GeometryConversor.geometryFromWKT(POINT_TORRE_HERCULES);
            towerHercules = tipService.create(MONUMENT_DISCRIMINATOR, name, description, VALID_TIP_PHOTO_URL, null, geom);

            name = "Alameda Santiago de Compostela";
            description = "Sitio verde";
            geom = (Point) GeometryConversor.geometryFromWKT(POINT_ALAMEDA);
            tipService.create(NATURAL_SPACE_DISCRIMINATOR, name, description, VALID_TIP_PHOTO_URL, null, geom);

            ArrayList<Long> typeIds = new ArrayList<Long>() {{
                add(MONUMENT_DISCRIMINATOR);
            }};
            List<TIPSearchDto> tipSearchDtos = tipService.find(null, typeIds, null, null, null, null);

            assertEquals(1, tipSearchDtos.size());
            assertEquals(towerHercules.getId(), tipSearchDtos.get(0).getId());

            typeIds = new ArrayList<Long>() {{
                add(MONUMENT_DISCRIMINATOR);
                add(NATURAL_SPACE_DISCRIMINATOR);
            }};
            tipSearchDtos = tipService.find(null, typeIds, null, null, null, null);

            assertEquals(2, tipSearchDtos.size());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void findTipsByCities() {
        TIPDetailsDto towerHercules;
        try {
            String name = "Tower of Hercules";
            String description = "Human Patrimony";
            Point geom = (Point) GeometryConversor.geometryFromWKT(POINT_TORRE_HERCULES);
            towerHercules = tipService.create(MONUMENT_DISCRIMINATOR, name, description, VALID_TIP_PHOTO_URL, null, geom);

            name = "Alameda Santiago de Compostela";
            description = "Sitio verde";
            geom = (Point) GeometryConversor.geometryFromWKT(POINT_ALAMEDA);
            tipService.create(NATURAL_SPACE_DISCRIMINATOR, name, description, VALID_TIP_PHOTO_URL, null, geom);

            name = "Catedral Santiago de Compostela";
            description = "Sitio de peregrinacion";
            geom = (Point) GeometryConversor.geometryFromWKT(POINT_CATEDRAL_SANTIAGO);
            tipService.create(MONUMENT_DISCRIMINATOR, name, description, VALID_TIP_PHOTO_URL, null, geom);

            ArrayList<Long> cityIds = new ArrayList<Long>() {{
                add(A_CORUNA_ID);
            }};
            List<TIPSearchDto> tipSearchDtos = tipService.find(null, null, cityIds, null, null, null);

            assertEquals(1, tipSearchDtos.size());
            assertEquals(towerHercules.getId(), tipSearchDtos.get(0).getId());

            cityIds = new ArrayList<Long>() {{
                add(SANTIAGO_ID);
            }};
            tipSearchDtos = tipService.find(null, null, cityIds, null, null, null);

            assertEquals(2, tipSearchDtos.size());

            cityIds = new ArrayList<Long>() {{
                add(A_CORUNA_ID);
                add(SANTIAGO_ID);
            }};
            tipSearchDtos = tipService.find(null, null, cityIds, null, null, null);

            assertEquals(3, tipSearchDtos.size());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void findTipsByBoundTypesCities() {
        TIPDetailsDto towerHercules;
        Geometry boundsGalicia;
        Geometry boundsNY;
        try {
            String name = "Tower of Hercules";
            String description = "Human Patrimony";
            Point geom = (Point) GeometryConversor.geometryFromWKT(POINT_TORRE_HERCULES);
            towerHercules = tipService.create(MONUMENT_DISCRIMINATOR, name, description, VALID_TIP_PHOTO_URL, null, geom);

            name = "Alameda Santiago de Compostela";
            description = "Sitio verde";
            geom = (Point) GeometryConversor.geometryFromWKT(POINT_ALAMEDA);
            tipService.create(NATURAL_SPACE_DISCRIMINATOR, name, description, VALID_TIP_PHOTO_URL, null, geom);

            name = "Catedral Santiago de Compostela";
            description = "Sitio de peregrinacion";
            geom = (Point) GeometryConversor.geometryFromWKT(POINT_CATEDRAL_SANTIAGO);
            tipService.create(MONUMENT_DISCRIMINATOR, name, description, VALID_TIP_PHOTO_URL, null, geom);

            name = "Liberty Statue";
            description = "NY symbol";
            geom = (Point) GeometryConversor.geometryFromWKT(POINT_STATUE_OF_LIBERTRY);
            tipService.create(MONUMENT_DISCRIMINATOR, name, description, VALID_TIP_PHOTO_URL, null, geom);

            boundsGalicia = GeometryConversor.geometryFromWKT(BOUNDS_GALICIA);
            boundsNY = GeometryConversor.geometryFromWKT(BOUNDS_NEW_YORK);

            ArrayList<Long> typeIds = new ArrayList<Long>() {{
                add(MONUMENT_DISCRIMINATOR);
            }};
            ArrayList<Long> cityIds = new ArrayList<Long>() {{
                add(A_CORUNA_ID);
            }};
            List<TIPSearchDto> tipSearchDtos = tipService.find(boundsGalicia, typeIds, cityIds, null, null, null);
            assertEquals(1, tipSearchDtos.size());
            assertEquals(towerHercules.getId(), tipSearchDtos.get(0).getId());

            typeIds = new ArrayList<Long>() {{
                add(MONUMENT_DISCRIMINATOR);
            }};
            cityIds = new ArrayList<Long>() {{
                add(NEW_YORK_ID);
            }};
            tipSearchDtos = tipService.find(boundsNY, typeIds, cityIds, null, null, null);
            assertEquals(1, tipSearchDtos.size());

            typeIds = new ArrayList<Long>() {{
                add(MONUMENT_DISCRIMINATOR);
            }};
            cityIds = new ArrayList<Long>() {{
                add(A_CORUNA_ID);
            }};
            tipSearchDtos = tipService.find(boundsNY, typeIds, cityIds, null, null, null);
            assertEquals(0, tipSearchDtos.size());

            typeIds = new ArrayList<Long>() {{
                add(NATURAL_SPACE_DISCRIMINATOR);
            }};
            cityIds = new ArrayList<Long>() {{
                add(NEW_YORK_ID);
            }};
            tipSearchDtos = tipService.find(boundsNY, typeIds, cityIds, null, null, null);
            assertEquals(0, tipSearchDtos.size());

            typeIds = new ArrayList<Long>() {{
                add(NATURAL_SPACE_DISCRIMINATOR);
            }};
            cityIds = new ArrayList<Long>() {{
                add(SANTIAGO_ID);
            }};
            tipSearchDtos = tipService.find(boundsGalicia, typeIds, cityIds, null, null, null);
            assertEquals(1, tipSearchDtos.size());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void getAllTIPtypes() {
        assertEquals(4, tipTypeService.findAllTypes().size());
    }

}
