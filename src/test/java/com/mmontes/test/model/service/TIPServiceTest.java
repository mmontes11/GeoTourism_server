package com.mmontes.test.model.service;

import com.mmontes.model.service.TIPService;
import com.mmontes.model.service.TIPtypeService;
import com.mmontes.util.GeometryUtils;
import com.mmontes.util.dto.FeatureSearchDto;
import com.mmontes.util.dto.TIPDetailsDto;
import com.mmontes.util.exception.*;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
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
    private TIPService tipService;

    @Autowired
    private TIPtypeService tipTypeService;

    private static TIPDetailsDto towerHercules;
    private static TIPDetailsDto alameda;
    private static TIPDetailsDto cathedral;
    private static TIPDetailsDto reisCatolicos;
    private static TIPDetailsDto statueOfLiberty;

    @Before
    public void createData(){
        try {
            String name = "Tower of Hercules";
            String description = "Human Patrimony";
            Geometry geom = GeometryUtils.geometryFromWKT(POINT_TORRE_HERCULES);
            towerHercules = tipService.create(MONUMENT_DISCRIMINATOR, name, description, VALID_TIP_PHOTO_URL, VALID_TIP_INFO_URL, geom);

            name = "Alameda Santiago de Compostela";
            description = "Sitio verde";
            geom = GeometryUtils.geometryFromWKT(POINT_ALAMEDA);
            alameda = tipService.create(NATURAL_SPACE_DISCRIMINATOR, name, description, VALID_TIP_PHOTO_URL, VALID_TIP_INFO_URL, geom);

            name = "Catedral Santiago de Compostela";
            description = "Sitio de peregrinacion";
            geom = GeometryUtils.geometryFromWKT(POINT_CATEDRAL_SANTIAGO);
            cathedral = tipService.create(MONUMENT_DISCRIMINATOR, name, description, VALID_TIP_PHOTO_URL, VALID_TIP_INFO_URL, geom);

            name = "Hotel Os Reis Catolicos";
            description = "5 estrelas";
            geom = GeometryUtils.geometryFromWKT(POINT_HOTEL_REIS_CATOLICOS);
            reisCatolicos = tipService.create(HOTEL_DISCRIMINATOR, name, description, VALID_TIP_PHOTO_URL, VALID_TIP_INFO_URL, geom);

            name = "Liberty Statue";
            description = "NY symbol";
            geom = GeometryUtils.geometryFromWKT(POINT_STATUE_OF_LIBERTRY);
            statueOfLiberty = tipService.create(MONUMENT_DISCRIMINATOR, name, description, VALID_TIP_PHOTO_URL, VALID_TIP_INFO_URL, geom);
        } catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void createBasicTIPs() {
        try {
            assertEquals(MONUMENT_DISCRIMINATOR, towerHercules.getType());
            assertNotNull(towerHercules.getGeom());
            assertEquals(VALID_TIP_PHOTO_URL, towerHercules.getPhotoUrl());
            assertEquals(VALID_TIP_INFO_URL, towerHercules.getInfoUrl());
            assertNotNull(towerHercules.getGoogleMapsUrl());

            assertEquals(NATURAL_SPACE_DISCRIMINATOR, alameda.getType());
            assertNotNull(alameda.getGeom());
            assertEquals(VALID_TIP_PHOTO_URL, alameda.getPhotoUrl());
            assertEquals(VALID_TIP_INFO_URL, alameda.getInfoUrl());
            assertNotNull(alameda.getGoogleMapsUrl());

            assertEquals(MONUMENT_DISCRIMINATOR, cathedral.getType());
            assertNotNull(cathedral.getGeom());
            assertEquals(VALID_TIP_PHOTO_URL, cathedral.getPhotoUrl());
            assertEquals(VALID_TIP_INFO_URL, cathedral.getInfoUrl());
            assertNotNull(cathedral.getGoogleMapsUrl());

            assertEquals(HOTEL_DISCRIMINATOR, reisCatolicos.getType());
            assertNotNull(reisCatolicos.getGeom());
            assertEquals(VALID_TIP_PHOTO_URL, reisCatolicos.getPhotoUrl());
            assertEquals(VALID_TIP_INFO_URL, reisCatolicos.getInfoUrl());
            assertNotNull(reisCatolicos.getGoogleMapsUrl());

            assertEquals(MONUMENT_DISCRIMINATOR, statueOfLiberty.getType());
            assertNotNull(statueOfLiberty.getGeom());
            assertEquals(VALID_TIP_PHOTO_URL, statueOfLiberty.getPhotoUrl());
            assertEquals(VALID_TIP_INFO_URL, statueOfLiberty.getInfoUrl());
            assertNotNull(statueOfLiberty.getGoogleMapsUrl());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test(expected = TIPLocationException.class)
    public void createTIPinNonCreatedCity() throws TIPLocationException {
        try {
            String name = "Alhambra";
            String description = "Patrimonio de la humanidad";
            Geometry geom = GeometryUtils.geometryFromWKT(POINT_ALHAMBRA);
            tipService.create(MONUMENT_DISCRIMINATOR, name, description, VALID_TIP_PHOTO_URL, VALID_TIP_INFO_URL, geom);
        } catch (InvalidTIPUrlException | GeometryParsingException | InstanceNotFoundException | GoogleMapsServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findTIPsByBounds() {
        Geometry bounds = null;
        try {
            bounds = GeometryUtils.geometryFromWKT(BOUNDS_SANTIAGO_DE_COMPOSTELA);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        List<FeatureSearchDto> featureSearchDtos = null;
        try {
            featureSearchDtos = tipService.find(bounds, null, null, null, null, null);
        } catch (InstanceNotFoundException e) {
            fail();
        }
        assertEquals(3, featureSearchDtos.size());
    }

    @Test
    public void finTipsFromFarCity() {
        Geometry bounds;
        List<FeatureSearchDto> featureSearchDtos = null;
        try {
            bounds = GeometryUtils.geometryFromWKT(BOUNDS_NEW_YORK);
            featureSearchDtos = tipService.find(bounds, null, null, null, null, null);
        } catch (Exception e) {
            fail();
        }
        assertEquals(1,featureSearchDtos.size());
    }

    @Test
    public void findTipsByTypes() {
        try {
            ArrayList<Long> typeIds = new ArrayList<Long>() {{
                add(MONUMENT_DISCRIMINATOR);
            }};
            List<FeatureSearchDto> featureSearchDtos = tipService.find(null, typeIds, null, null, null, null);
            assertEquals(3, featureSearchDtos.size());

            typeIds = new ArrayList<Long>() {{
                add(MONUMENT_DISCRIMINATOR);
                add(NATURAL_SPACE_DISCRIMINATOR);
            }};
            featureSearchDtos = tipService.find(null, typeIds, null, null, null, null);
            assertEquals(4, featureSearchDtos.size());

            typeIds = new ArrayList<Long>() {{
                add(MONUMENT_DISCRIMINATOR);
                add(NATURAL_SPACE_DISCRIMINATOR);
                add(HOTEL_DISCRIMINATOR);
            }};
            featureSearchDtos = tipService.find(null, typeIds, null, null, null, null);
            assertEquals(5, featureSearchDtos.size());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void findTipsByCities() {
        try {
            ArrayList<Long> cityIds = new ArrayList<Long>() {{
                add(A_CORUNA_ID);
            }};
            List<FeatureSearchDto> featureSearchDtos = tipService.find(null, null, cityIds, null, null, null);
            assertEquals(1, featureSearchDtos.size());

            cityIds = new ArrayList<Long>() {{
                add(SANTIAGO_ID);
            }};
            featureSearchDtos = tipService.find(null, null, cityIds, null, null, null);
            assertEquals(3, featureSearchDtos.size());

            cityIds = new ArrayList<Long>() {{
                add(A_CORUNA_ID);
                add(SANTIAGO_ID);
            }};
            featureSearchDtos = tipService.find(null, null, cityIds, null, null, null);
            assertEquals(4, featureSearchDtos.size());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void findTipsByBoundTypesCities() {
        Geometry boundsGalicia;
        Geometry boundsNY;
        try {
            boundsGalicia = GeometryUtils.geometryFromWKT(BOUNDS_GALICIA);
            boundsNY = GeometryUtils.geometryFromWKT(BOUNDS_NEW_YORK);

            ArrayList<Long> typeIds = new ArrayList<Long>() {{
                add(MONUMENT_DISCRIMINATOR);
            }};
            ArrayList<Long> cityIds = new ArrayList<Long>() {{
                add(A_CORUNA_ID);
            }};
            List<FeatureSearchDto> featureSearchDtos = tipService.find(boundsGalicia, typeIds, cityIds, null, null, null);
            assertEquals(1, featureSearchDtos.size());

            typeIds = new ArrayList<Long>() {{
                add(MONUMENT_DISCRIMINATOR);
            }};
            cityIds = new ArrayList<Long>() {{
                add(NEW_YORK_ID);
            }};
            featureSearchDtos = tipService.find(boundsNY, typeIds, cityIds, null, null, null);
            assertEquals(1, featureSearchDtos.size());

            typeIds = new ArrayList<Long>() {{
                add(NATURAL_SPACE_DISCRIMINATOR);
            }};
            cityIds = new ArrayList<Long>() {{
                add(NEW_YORK_ID);
            }};
            featureSearchDtos = tipService.find(boundsNY, typeIds, cityIds, null, null, null);
            assertEquals(0, featureSearchDtos.size());

            typeIds = new ArrayList<Long>() {{
                add(NATURAL_SPACE_DISCRIMINATOR);
                add(MONUMENT_DISCRIMINATOR);
                add(HOTEL_DISCRIMINATOR);
            }};
            cityIds = new ArrayList<Long>() {{
                add(SANTIAGO_ID);
            }};
            featureSearchDtos = tipService.find(boundsGalicia, typeIds, cityIds, null, null, null);
            assertEquals(3, featureSearchDtos.size());
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
