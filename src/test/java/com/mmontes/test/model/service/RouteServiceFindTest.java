package com.mmontes.test.model.service;

import com.mmontes.model.dao.RouteDao;
import com.mmontes.model.service.RouteService;
import com.mmontes.model.service.TIPService;
import com.mmontes.util.GeometryUtils;
import com.mmontes.util.dto.FeatureSearchDto;
import com.mmontes.util.dto.RouteDetailsDto;
import com.mmontes.util.dto.TIPDetailsDto;
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE})
@Transactional
public class RouteServiceFindTest {

    public static Geometry boundsGalicia;
    public static Geometry boundsCoruna;
    public static Geometry boundsSantiago;
    public static Geometry boundsNY;
    private static Long alamedaID;
    private static Long cathedralID;
    private static Long reisCatolicosID;
    private static Long towerOfHerculesID;
    private static Long hotelRiazorID;
    private static Long almaNegraID;
    private static Long alamedaToReisCatolicosID;
    private static Long hotelRiazorToTorreHerculesID;
    @Autowired
    private RouteService routeService;

    @Autowired
    private RouteDao routeDao;

    @Autowired
    private TIPService tipService;

    @Before
    @SuppressWarnings("all")
    public void createData() {
        try {
            String name = "Alameda Park";
            String description = "Green zone";
            Geometry geom = GeometryUtils.geometryFromWKT(POINT_ALAMEDA);
            TIPDetailsDto tipDetailsDto = tipService.create(NATURAL_SPACE_DISCRIMINATOR, name, description, VALID_TIP_PHOTO_URL, VALID_TIP_INFO_URL, geom);
            alamedaID = tipDetailsDto.getId();

            name = "Catedral Santiago de Compostela cathedral";
            description = "Human patrimony";
            geom = GeometryUtils.geometryFromWKT(POINT_CATEDRAL_SANTIAGO);
            tipDetailsDto = tipService.create(MONUMENT_DISCRIMINATOR, name, description, VALID_TIP_PHOTO_URL, VALID_TIP_INFO_URL, geom);
            cathedralID = tipDetailsDto.getId();

            name = "Hotel Os Reis Catolicos";
            description = "5 estrelas";
            geom = GeometryUtils.geometryFromWKT(POINT_HOTEL_REIS_CATOLICOS);
            tipDetailsDto = tipService.create(HOTEL_DISCRIMINATOR, name, description, VALID_TIP_PHOTO_URL, null, geom);
            reisCatolicosID = tipDetailsDto.getId();

            name = "Tower of Hercules";
            description = "Human Patrimony";
            geom = GeometryUtils.geometryFromWKT(POINT_TORRE_HERCULES);
            tipDetailsDto = tipService.create(MONUMENT_DISCRIMINATOR, name, description, VALID_TIP_PHOTO_URL, VALID_TIP_INFO_URL, geom);
            towerOfHerculesID = tipDetailsDto.getId();

            name = "Hotel Riazor";
            description = "5 stars";
            geom = GeometryUtils.geometryFromWKT(POINT_HOTEL_RIAZOR);
            tipDetailsDto = tipService.create(HOTEL_DISCRIMINATOR, name, description, VALID_TIP_PHOTO_URL, VALID_TIP_INFO_URL, geom);
            hotelRiazorID = tipDetailsDto.getId();

            name = "Restaurante Alma Negra";
            description = "Yummy";
            geom = GeometryUtils.geometryFromWKT(POINT_ALMA_NEGRA);
            tipDetailsDto = tipService.create(RESTAURANT_DISCRIMINATOR, name, description, VALID_TIP_PHOTO_URL, VALID_TIP_INFO_URL, geom);
            almaNegraID = tipDetailsDto.getId();

            name = "From Alameda To Reis catolicos";
            description = "Santiago route";
            String travelMode = WALKING_TRAVEL_MODE;
            List<Long> tipIds = new ArrayList<>();
            tipIds.add(alamedaID);
            tipIds.add(cathedralID);
            tipIds.add(reisCatolicosID);
            RouteDetailsDto routeDetailsDto = routeService.create(name, description, travelMode, null, tipIds, EXISTING_FACEBOOK_USER_ID);
            alamedaToReisCatolicosID = routeDetailsDto.getId();

            name = "From Hotel Riazor ";
            description = "Santiago route";
            travelMode = DRIVING_TRAVEL_MODE;
            tipIds.clear();
            tipIds.add(hotelRiazorID);
            tipIds.add(almaNegraID);
            tipIds.add(towerOfHerculesID);
            routeDetailsDto = routeService.create(name, description, travelMode, null, tipIds, EXISTING_FACEBOOK_USER_ID2);
            hotelRiazorToTorreHerculesID = routeDetailsDto.getId();

            boundsGalicia = GeometryUtils.geometryFromWKT(BOUNDS_GALICIA);
            boundsCoruna = GeometryUtils.geometryFromWKT(BOUNDS_A_CORUNA);
            boundsSantiago = GeometryUtils.geometryFromWKT(BOUNDS_SANTIAGO_DE_COMPOSTELA);
            boundsNY = GeometryUtils.geometryFromWKT(BOUNDS_NEW_YORK);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void findRoutesByBounds() {
        try {
            List<FeatureSearchDto> featureSearchDtos = routeService.find(boundsGalicia, null, null, null, null);
            assertEquals(2, featureSearchDtos.size());

            featureSearchDtos = routeService.find(boundsCoruna, null, null, null, null);
            assertEquals(1, featureSearchDtos.size());

            featureSearchDtos = routeService.find(boundsSantiago, null, null, null, null);
            assertEquals(1, featureSearchDtos.size());

            featureSearchDtos = routeService.find(boundsNY, null, null, null, null);
            assertEquals(0, featureSearchDtos.size());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void findRoutesByTravelMode() {
        try {
            List<String> travelModes = new ArrayList<>();
            travelModes.add(WALKING_TRAVEL_MODE);
            List<FeatureSearchDto> featureSearchDtos = routeService.find(null, travelModes, null, null, null);
            assertEquals(1, featureSearchDtos.size());

            travelModes.clear();
            travelModes.add(DRIVING_TRAVEL_MODE);
            featureSearchDtos = routeService.find(null, travelModes, null, null, null);
            assertEquals(1, featureSearchDtos.size());

            travelModes.clear();
            travelModes.add(DRIVING_TRAVEL_MODE);
            travelModes.add(WALKING_TRAVEL_MODE);
            featureSearchDtos = routeService.find(null, travelModes, null, null, null);
            assertEquals(2, featureSearchDtos.size());

            travelModes.clear();
            travelModes.add(BICYCLING_TRAVEL_MODE);
            featureSearchDtos = routeService.find(null, travelModes, null, null, null);
            assertEquals(0, featureSearchDtos.size());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void findRoutesCreatedByMe() {
        try {
            List<FeatureSearchDto> featureSearchDtos = routeService.find(null, null, 0, EXISTING_FACEBOOK_USER_ID, null);
            assertEquals(1, featureSearchDtos.size());

            featureSearchDtos = routeService.find(null, null, 0, EXISTING_FACEBOOK_USER_ID2, null);
            assertEquals(1, featureSearchDtos.size());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void findRoutesCreatedByMyFriends() {
        try {
            List<Long> facebookUserIds = new ArrayList<>();
            facebookUserIds.add(EXISTING_FACEBOOK_USER_ID);
            List<FeatureSearchDto> featureSearchDtos = routeService.find(null, null, 1, null, facebookUserIds);
            assertEquals(1, featureSearchDtos.size());

            facebookUserIds.clear();
            facebookUserIds.add(EXISTING_FACEBOOK_USER_ID2);
            featureSearchDtos = routeService.find(null, null, 1, null, facebookUserIds);
            assertEquals(1, featureSearchDtos.size());

            facebookUserIds.clear();
            facebookUserIds.add(EXISTING_FACEBOOK_USER_ID);
            facebookUserIds.add(EXISTING_FACEBOOK_USER_ID2);
            featureSearchDtos = routeService.find(null, null, 1, null, facebookUserIds);
            assertEquals(2, featureSearchDtos.size());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void findRoutesWithAllParams() {
        try {
            List<FeatureSearchDto> featureSearchDtos = routeService.find(null, null, null, null, null);
            assertEquals(2, featureSearchDtos.size());

            List<String> travelModes = new ArrayList<>();
            travelModes.add(WALKING_TRAVEL_MODE);
            featureSearchDtos = routeService.find(boundsGalicia, travelModes, 0, EXISTING_FACEBOOK_USER_ID, null);
            assertEquals(1, featureSearchDtos.size());

            List<Long> facebookUserIds = new ArrayList<>();
            facebookUserIds.add(EXISTING_FACEBOOK_USER_ID2);
            featureSearchDtos = routeService.find(boundsGalicia, travelModes, 1, null, facebookUserIds);
            assertEquals(0, featureSearchDtos.size());

            travelModes.clear();
            facebookUserIds.clear();
            travelModes.add(WALKING_TRAVEL_MODE);
            travelModes.add(DRIVING_TRAVEL_MODE);
            facebookUserIds.add(EXISTING_FACEBOOK_USER_ID);
            facebookUserIds.add(EXISTING_FACEBOOK_USER_ID2);
            featureSearchDtos = routeService.find(boundsCoruna, travelModes, 1, null, facebookUserIds);
            assertEquals(1, featureSearchDtos.size());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
