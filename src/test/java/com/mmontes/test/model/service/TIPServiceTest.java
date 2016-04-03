package com.mmontes.test.model.service;

import com.mmontes.model.service.FavouriteService;
import com.mmontes.model.service.RouteService;
import com.mmontes.model.service.TIPService;
import com.mmontes.model.service.TIPtypeService;
import com.mmontes.util.GeometryUtils;
import com.mmontes.util.dto.FeatureSearchDto;
import com.mmontes.util.dto.TIPDetailsDto;
import com.mmontes.util.exception.GeometryParsingException;
import com.mmontes.util.exception.InstanceNotFoundException;
import com.mmontes.util.exception.InvalidTIPUrlException;
import com.mmontes.util.exception.TIPLocationException;
import com.vividsolutions.jts.geom.Geometry;
import org.junit.Before;
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
@SuppressWarnings("all")
public class TIPServiceTest {

    private static TIPDetailsDto towerHercules;
    private static TIPDetailsDto alameda;
    private static TIPDetailsDto cathedral;
    private static TIPDetailsDto reisCatolicos;
    private static TIPDetailsDto statueOfLiberty;
    private static TIPDetailsDto unreviewed;
    @Autowired
    private TIPService tipService;
    @Autowired
    private TIPtypeService tipTypeService;
    @Autowired
    private FavouriteService favouriteService;
    @Autowired
    private RouteService routeService;

    @Before
    public void createData() {
        try {
            String name = "Tower of Hercules";
            String description = "Human Patrimony";
            Geometry geom = GeometryUtils.geometryFromWKT(POINT_TORRE_HERCULES);
            towerHercules = tipService.create(MONUMENT_DISCRIMINATOR, name, description, VALID_TIP_PHOTO_URL, VALID_TIP_INFO_URL, geom, null, true);

            name = "Alameda Santiago de Compostela";
            description = "Sitio verde";
            geom = GeometryUtils.geometryFromWKT(POINT_ALAMEDA);
            alameda = tipService.create(NATURAL_SPACE_DISCRIMINATOR, name, description, VALID_TIP_PHOTO_URL, VALID_TIP_INFO_URL, geom, null, true);

            name = "Catedral Santiago de Compostela";
            description = "Sitio de peregrinacion";
            geom = GeometryUtils.geometryFromWKT(POINT_CATEDRAL_SANTIAGO);
            cathedral = tipService.create(MONUMENT_DISCRIMINATOR, name, description, VALID_TIP_PHOTO_URL, VALID_TIP_INFO_URL, geom, null, true);

            name = "Hotel Os Reis Catolicos";
            description = "5 estrelas";
            geom = GeometryUtils.geometryFromWKT(POINT_HOTEL_REIS_CATOLICOS);
            reisCatolicos = tipService.create(HOTEL_DISCRIMINATOR, name, description, VALID_TIP_PHOTO_URL, VALID_TIP_INFO_URL, geom, null, true);

            name = "Liberty Statue";
            description = "NY symbol";
            geom = GeometryUtils.geometryFromWKT(POINT_STATUE_OF_LIBERTRY);
            statueOfLiberty = tipService.create(MONUMENT_DISCRIMINATOR, name, description, VALID_TIP_PHOTO_URL, VALID_TIP_INFO_URL, geom, null, true);

            name = "Unreviewed Place";
            description = "Unreviewed";
            unreviewed = tipService.create(MONUMENT_DISCRIMINATOR, name, description, VALID_TIP_PHOTO_URL, VALID_TIP_INFO_URL, geom, null, false);

            favouriteService.markAsFavourite(statueOfLiberty.getId(), EXISTING_FACEBOOK_USER_ID);
            favouriteService.markAsFavourite(towerHercules.getId(), EXISTING_FACEBOOK_USER_ID2);
        } catch (Exception e) {
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
            tipService.create(MONUMENT_DISCRIMINATOR, name, description, VALID_TIP_PHOTO_URL, VALID_TIP_INFO_URL, geom, null, true);
        } catch (InvalidTIPUrlException | GeometryParsingException | InstanceNotFoundException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void editTIP() {
        try {
            Long newType = NATURAL_SPACE_DISCRIMINATOR;
            String newName = "New name";
            String newDescription = "New description";
            String newInfoUrl = VALID_TIP_INFO_URL;
            String newAddress = "Fake street";
            String newPhotoUrl = VALID_TIP_PHOTO_URL;
            TIPDetailsDto tipDetailsDto = tipService.edit(statueOfLiberty.getId(), null, newType, newName, newDescription, newInfoUrl, newAddress, newPhotoUrl);

            assertEquals(newType, tipDetailsDto.getType());
            assertEquals(newName, tipDetailsDto.getName());
            assertEquals(newDescription, tipDetailsDto.getDescription());
            assertEquals(newInfoUrl, tipDetailsDto.getInfoUrl());
            assertEquals(newAddress, tipDetailsDto.getAddress());
            assertEquals(newPhotoUrl, tipDetailsDto.getPhotoUrl());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test(expected = InstanceNotFoundException.class)
    public void removeTIP() throws InstanceNotFoundException {
        TIPDetailsDto tipDetailsDto = null;
        try {
            tipDetailsDto = tipService.findById(statueOfLiberty.getId(), null);
            tipService.remove(tipDetailsDto.getId());
        } catch (Exception e) {
            fail();
        }
        tipService.findById(tipDetailsDto.getId(), null);
    }

    @Test
    public void findTIPsByBounds() {
        List<FeatureSearchDto> featureSearchDtos;
        try {
            featureSearchDtos = tipService.find(BOUNDS_SANTIAGO_DE_COMPOSTELA, null, null, null, null, true);
            assertEquals(3, featureSearchDtos.size());
            featureSearchDtos = tipService.find(BOUNDS_NEW_YORK, null, null, null, null, true);
            assertEquals(1, featureSearchDtos.size());
        } catch (InstanceNotFoundException e) {
            fail();
        }
    }

    @Test
    public void findTipsByTypes() {
        try {
            ArrayList<Long> typeIds = new ArrayList<Long>() {{
                add(MONUMENT_DISCRIMINATOR);
            }};
            List<FeatureSearchDto> featureSearchDtos = tipService.find(null, typeIds, null, null, null, true);
            assertEquals(3, featureSearchDtos.size());

            typeIds = new ArrayList<Long>() {{
                add(MONUMENT_DISCRIMINATOR);
                add(NATURAL_SPACE_DISCRIMINATOR);
            }};
            featureSearchDtos = tipService.find(null, typeIds, null, null, null, true);
            assertEquals(4, featureSearchDtos.size());

            typeIds = new ArrayList<Long>() {{
                add(MONUMENT_DISCRIMINATOR);
                add(NATURAL_SPACE_DISCRIMINATOR);
                add(HOTEL_DISCRIMINATOR);
            }};
            featureSearchDtos = tipService.find(null, typeIds, null, null, null, true);
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
            List<FeatureSearchDto> featureSearchDtos = tipService.find(null, null, cityIds, null, null, true);
            assertEquals(1, featureSearchDtos.size());

            cityIds = new ArrayList<Long>() {{
                add(SANTIAGO_ID);
            }};
            featureSearchDtos = tipService.find(null, null, cityIds, null, null, true);
            assertEquals(3, featureSearchDtos.size());

            cityIds = new ArrayList<Long>() {{
                add(A_CORUNA_ID);
                add(SANTIAGO_ID);
            }};
            featureSearchDtos = tipService.find(null, null, cityIds, null, null, true);
            assertEquals(4, featureSearchDtos.size());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void findTipsFavouritedByFacebookUserIDs() {
        try {
            List<Long> facebookUserIDs = new ArrayList<>();
            facebookUserIDs.add(EXISTING_FACEBOOK_USER_ID);
            List<FeatureSearchDto> featureSearchDtos = tipService.find(null, null, null, facebookUserIDs, null, true);
            assertEquals(1, featureSearchDtos.size());

            facebookUserIDs.clear();
            facebookUserIDs.add(EXISTING_FACEBOOK_USER_ID2);
            featureSearchDtos = tipService.find(null, null, null, facebookUserIDs, null, true);
            assertEquals(0, featureSearchDtos.size());

            facebookUserIDs.clear();
            facebookUserIDs.add(EXISTING_FACEBOOK_USER_ID);
            facebookUserIDs.add(EXISTING_FACEBOOK_USER_ID2);
            featureSearchDtos = tipService.find(null, null, null, facebookUserIDs, null, true);
            assertEquals(1, featureSearchDtos.size());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void findTipsByAllParams() {
        try {
            ArrayList<Long> typeIds = new ArrayList<Long>() {{
                add(MONUMENT_DISCRIMINATOR);
            }};
            ArrayList<Long> cityIds = new ArrayList<Long>() {{
                add(A_CORUNA_ID);
            }};
            ArrayList<Long> facebookUserIds = new ArrayList<Long>() {{
                add(EXISTING_FACEBOOK_USER_ID2);
            }};
            List<FeatureSearchDto> featureSearchDtos = tipService.find(BOUNDS_GALICIA, typeIds, cityIds, facebookUserIds, null, true);
            assertEquals(0, featureSearchDtos.size());


            typeIds = new ArrayList<Long>() {{
                add(MONUMENT_DISCRIMINATOR);
            }};
            cityIds = new ArrayList<Long>() {{
                add(NEW_YORK_ID);
            }};
            facebookUserIds = new ArrayList<Long>() {{
                add(EXISTING_FACEBOOK_USER_ID);
            }};
            featureSearchDtos = tipService.find(BOUNDS_NEW_YORK, typeIds, cityIds, facebookUserIds, null, true);
            assertEquals(1, featureSearchDtos.size());


            typeIds = new ArrayList<Long>() {{
                add(NATURAL_SPACE_DISCRIMINATOR);
            }};
            cityIds = new ArrayList<Long>() {{
                add(NEW_YORK_ID);
            }};
            facebookUserIds = new ArrayList<Long>() {{
                add(EXISTING_FACEBOOK_USER_ID);
                add(EXISTING_FACEBOOK_USER_ID2);
            }};
            featureSearchDtos = tipService.find(BOUNDS_NEW_YORK, typeIds, cityIds, facebookUserIds, null, true);
            assertEquals(0, featureSearchDtos.size());


            typeIds = new ArrayList<Long>() {{
                add(NATURAL_SPACE_DISCRIMINATOR);
                add(MONUMENT_DISCRIMINATOR);
                add(HOTEL_DISCRIMINATOR);
            }};
            cityIds = new ArrayList<Long>() {{
                add(SANTIAGO_ID);
            }};
            featureSearchDtos = tipService.find(BOUNDS_GALICIA, typeIds, cityIds, null, null, true);
            assertEquals(3, featureSearchDtos.size());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void findNonReviewedTIPs(){
        List<FeatureSearchDto> featureSearchDtos = null;
        try {
            featureSearchDtos = tipService.find(null, null, null, null, null, false);
            assertEquals(1, featureSearchDtos.size());
        } catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void reviewTIP(){
        List<FeatureSearchDto> featureSearchDtos = null;
        try {
            featureSearchDtos = tipService.find(null, null, null, null, null, true);
            assertEquals(5, featureSearchDtos.size());

            tipService.review(unreviewed.getId());
            featureSearchDtos = tipService.find(null, null, null, null, null, true);
            assertEquals(6, featureSearchDtos.size());
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void getAllTIPtypes() {
        assertEquals(4, tipTypeService.findAllTypes().size());
    }
}
