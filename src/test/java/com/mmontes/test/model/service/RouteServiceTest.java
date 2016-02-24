package com.mmontes.test.model.service;

import com.mmontes.model.dao.RouteDao;
import com.mmontes.model.service.RouteService;
import com.mmontes.model.service.TIPService;
import com.mmontes.util.Constants;
import com.mmontes.util.GeometryUtils;
import com.mmontes.util.dto.RouteDetailsDto;
import com.mmontes.util.dto.TIPDetailsDto;
import com.mmontes.util.dto.TIPRouteDto;
import com.mmontes.util.exception.InstanceNotFoundException;
import com.mmontes.util.exception.InvalidRouteException;
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
import java.util.Objects;

import static com.mmontes.test.util.Constants.*;
import static com.mmontes.util.Constants.SPRING_CONFIG_FILE;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE})
@Transactional
public class RouteServiceTest {

    private static Long alamedaID;
    private static Long cathedralID;
    private static Long towerOfHerculesID;
    private static Long reisCatolicosID;
    private static Long statueOfLibertyID;

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
            TIPDetailsDto tipDetailsDto = tipService.create(NATURAL_SPACE_DISCRIMINATOR, name, description, VALID_TIP_PHOTO_URL, VALID_TIP_INFO_URL, geom, null);
            alamedaID = tipDetailsDto.getId();

            name = "Catedral Santiago de Compostela cathedral";
            description = "Human patrimony";
            geom = GeometryUtils.geometryFromWKT(POINT_CATEDRAL_SANTIAGO);
            tipDetailsDto = tipService.create(MONUMENT_DISCRIMINATOR, name, description, VALID_TIP_PHOTO_URL, VALID_TIP_INFO_URL, geom, null);
            cathedralID = tipDetailsDto.getId();

            name = "Hotel Os Reis Catolicos";
            description = "5 estrelas";
            geom = GeometryUtils.geometryFromWKT(POINT_HOTEL_REIS_CATOLICOS);
            tipDetailsDto = tipService.create(HOTEL_DISCRIMINATOR, name, description, VALID_TIP_PHOTO_URL, null, geom, null);
            reisCatolicosID = tipDetailsDto.getId();

            name = "Tower of Hercules";
            description = "Human Patrimony";
            geom = GeometryUtils.geometryFromWKT(POINT_TORRE_HERCULES);
            tipDetailsDto = tipService.create(MONUMENT_DISCRIMINATOR, name, description, VALID_TIP_PHOTO_URL, VALID_TIP_INFO_URL, geom, null);
            towerOfHerculesID = tipDetailsDto.getId();

            name = "Liberty Statue";
            description = "NY symbol";
            geom = GeometryUtils.geometryFromWKT(POINT_STATUE_OF_LIBERTRY);
            tipDetailsDto = tipService.create(MONUMENT_DISCRIMINATOR, name, description, VALID_TIP_PHOTO_URL, null, geom, null);
            statueOfLibertyID = tipDetailsDto.getId();
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void createRouteFromTIPS() {
        try {
            String name = "From Alameda To Tower of Hercules";
            String description = "Santiago route";
            String travelMode = WALKING_TRAVEL_MODE;
            List<Long> tipIds = new ArrayList<>();
            tipIds.add(alamedaID);
            tipIds.add(cathedralID);
            tipIds.add(towerOfHerculesID);
            RouteDetailsDto routeDetailsDto = routeService.create(EXISTING_FACEBOOK_USER_ID, name, description, travelMode, null, tipIds);

            assertNotNull(routeDetailsDto.getId());
            assertEquals(name, routeDetailsDto.getName());
            assertEquals(description, routeDetailsDto.getDescription());
            assertEquals(travelMode, routeDetailsDto.getTravelMode());
            assertNotNull(routeDetailsDto.getGeom());
            assertNotNull(routeDetailsDto.getGoogleMapsUrl());
            assertEquals(3, routeDetailsDto.getTips().size());
            assertEquals(EXISTING_FACEBOOK_USER_ID, routeDetailsDto.getCreator().getFacebookUserId());

            List<TIPRouteDto> tipRouteDtos = routeService.getTIPsInOrder(routeDetailsDto.getId());
            for (int i = 0; i < tipIds.size(); i++) {
                Long tipId = tipIds.get(i);
                TIPRouteDto retrievedTipIds = tipRouteDtos.get(i);
                assertEquals(tipId, retrievedTipIds.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test(expected = InvalidRouteException.class)
    public void createInvalidRoute() throws InvalidRouteException {
        String name = "From Alameda To Cathedral";
        String description = "Santiago route";
        String travelMode = WALKING_TRAVEL_MODE;
        List<Long> tipIds = new ArrayList<>();
        tipIds.add(alamedaID);
        try {
            routeService.create(EXISTING_FACEBOOK_USER_ID, name, description, travelMode, null, tipIds);
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test(expected = InvalidRouteException.class)
    public void createImpossibleRoute() throws InvalidRouteException {
        String name = "From Alameda To NY";
        String description = "Impossible route";
        String travelMode = WALKING_TRAVEL_MODE;
        List<Long> tipIds = new ArrayList<>();
        tipIds.add(alamedaID);
        tipIds.add(statueOfLibertyID);
        try {
            routeService.create(EXISTING_FACEBOOK_USER_ID, name, description, travelMode, null, tipIds);
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void editRoute() {
        try {
            String name = "From Alameda To Cathedral";
            String description = "Santiago route";
            String travelMode = WALKING_TRAVEL_MODE;
            List<Long> tipIds = new ArrayList<>();
            tipIds.add(alamedaID);
            tipIds.add(cathedralID);
            tipIds.add(towerOfHerculesID);
            RouteDetailsDto routeDetailsDto = routeService.create(EXISTING_FACEBOOK_USER_ID, name, description, travelMode, null, tipIds);

            name = "newName";
            description = "newDescription";
            travelMode = "walking";
            tipIds = new ArrayList<>();
            tipIds.add(cathedralID);
            tipIds.add(alamedaID);
            RouteDetailsDto editedRoute = routeService.edit(routeDetailsDto.getId(), EXISTING_FACEBOOK_USER_ID, name, description, travelMode, null, tipIds);
            Geometry geomRoute = GeometryUtils.geometryFromWKT(editedRoute.getGeom());

            assertNotNull(editedRoute.getId());
            assertEquals(name, editedRoute.getName());
            assertEquals(description, editedRoute.getDescription());
            assertEquals(travelMode, editedRoute.getTravelMode());
            assertNotNull(editedRoute.getGeom());
            assertNotNull(editedRoute.getGoogleMapsUrl());
            assertEquals(2, editedRoute.getTips().size());
            assertEquals(EXISTING_FACEBOOK_USER_ID, routeDetailsDto.getCreator().getFacebookUserId());

            List<TIPRouteDto> tipRouteDtos = routeService.getTIPsInOrder(routeDetailsDto.getId());
            for (int i = 0; i < tipIds.size(); i++) {
                Long tipId = tipIds.get(i);
                TIPRouteDto retrievedTipId = tipRouteDtos.get(i);
                Geometry geomTIP = GeometryUtils.geometryFromWKT(retrievedTipId.getGeom());
                assertEquals(tipId, retrievedTipId.getId());
                assertTrue(geomRoute.isWithinDistance(geomTIP, Constants.MIN_DISTANCE));
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void editRouteFromPartialGeoms() {
        try {
            String name = "From Alameda To Cathedral";
            String description = "Santiago route";
            String travelMode = WALKING_TRAVEL_MODE;
            List<Long> tipIds = new ArrayList<>();
            tipIds.add(alamedaID);
            tipIds.add(cathedralID);
            tipIds.add(towerOfHerculesID);
            RouteDetailsDto routeDetailsDto = routeService.create(EXISTING_FACEBOOK_USER_ID, name, description, travelMode, null, tipIds);

            List<String> partialGeomsString = new ArrayList<String>(){{
                add(LINESTRING_PARTIAL_1);
                add(LINESTRING_PARTIAL_2);
            }};
            List<Geometry> partialGeoms = new ArrayList<>();
            for(String geomString : partialGeomsString){
                partialGeoms.add(GeometryUtils.geometryFromWKT(geomString));
            }
            String newName = "newName";
            String newDescription = "description";
            routeDetailsDto = routeService.edit(routeDetailsDto.getId(),EXISTING_FACEBOOK_USER_ID,newName,newDescription,travelMode,partialGeoms,tipIds);
            Geometry routeGeom = GeometryUtils.geometryFromWKT(routeDetailsDto.getGeom());

            assertEquals(newName,routeDetailsDto.getName());
            assertEquals(newDescription,routeDetailsDto.getDescription());
            for(Geometry partialGeom : partialGeoms){
                assertTrue(routeGeom.contains(partialGeom));
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test(expected = InstanceNotFoundException.class)
    public void editRouteOfOtherUser() throws InstanceNotFoundException {
        try {
            String name = "From Alameda To Cathedral";
            String description = "Santiago route";
            String travelMode = WALKING_TRAVEL_MODE;
            List<Long> tipIds = new ArrayList<>();
            tipIds.add(alamedaID);
            tipIds.add(cathedralID);
            tipIds.add(towerOfHerculesID);
            RouteDetailsDto routeDetailsDto = routeService.create(EXISTING_FACEBOOK_USER_ID, name, description, travelMode, null, tipIds);

            name = "newName";
            description = "newDescription";
            travelMode = "walking";
            tipIds = new ArrayList<>();
            tipIds.add(cathedralID);
            tipIds.add(alamedaID);
            routeService.edit(routeDetailsDto.getId(), EXISTING_FACEBOOK_USER_ID2, name, description, travelMode, null, tipIds);
        } catch (InvalidRouteException e) {
            fail();
        }
    }

    @Test(expected = InstanceNotFoundException.class)
    public void removeRoute() throws InstanceNotFoundException {
        try {
            String name = "From Alameda To Cathedral";
            String description = "Santiago route";
            String travelMode = WALKING_TRAVEL_MODE;
            List<Long> tipIds = new ArrayList<>();
            tipIds.add(alamedaID);
            tipIds.add(cathedralID);
            tipIds.add(towerOfHerculesID);
            RouteDetailsDto routeDetailsDto = routeService.create(EXISTING_FACEBOOK_USER_ID, name, description, travelMode, null, tipIds);
            routeService.remove(routeDetailsDto.getId(), EXISTING_FACEBOOK_USER_ID);
            try {
                assertNotNull(tipService.findById(alamedaID, null));
                assertNotNull(tipService.findById(cathedralID, null));
                assertNotNull(tipService.findById(towerOfHerculesID, null));
            } catch (InstanceNotFoundException e) {
                fail();
            }
            routeDao.findById(routeDetailsDto.getId());
        } catch (InvalidRouteException e) {
            fail();
        }
    }

    @Test(expected = InstanceNotFoundException.class)
    public void removeRouteOfOtherUser() throws InstanceNotFoundException {
        try {
            String name = "From Alameda To Cathedral";
            String description = "Santiago route";
            String travelMode = WALKING_TRAVEL_MODE;
            List<Long> tipIds = new ArrayList<>();
            tipIds.add(alamedaID);
            tipIds.add(cathedralID);
            tipIds.add(towerOfHerculesID);
            RouteDetailsDto routeDetailsDto = routeService.create(EXISTING_FACEBOOK_USER_ID, name, description, travelMode, null, tipIds);
            routeService.remove(routeDetailsDto.getId(), EXISTING_FACEBOOK_USER_ID2);
        } catch (InvalidRouteException e) {
            fail();
        }
    }

    @Test(expected = InstanceNotFoundException.class)
    public void removeTIPfromRouteAndRemove() throws InstanceNotFoundException {
        try {
            String name = "From Alameda To Cathedral";
            String description = "Santiago route";
            String travelMode = WALKING_TRAVEL_MODE;
            List<Long> tipIds = new ArrayList<>();
            tipIds.add(cathedralID);
            tipIds.add(towerOfHerculesID);
            RouteDetailsDto routeDetailsDto = routeService.create(EXISTING_FACEBOOK_USER_ID, name, description, travelMode, null, tipIds);

            tipService.remove(cathedralID);
            routeDao.findById(routeDetailsDto.getId());
        } catch (InvalidRouteException e) {
            fail();
        }
    }

    @Test
    public void removeTIPfromRouteAndUpdate() throws InstanceNotFoundException {
        try {
            String name = "From Alameda To Cathedral";
            String description = "Santiago route";
            String travelMode = WALKING_TRAVEL_MODE;
            List<Long> tipIds = new ArrayList<>();
            tipIds.add(alamedaID);
            tipIds.add(cathedralID);
            tipIds.add(reisCatolicosID);
            RouteDetailsDto routeDetailsDto = routeService.create(EXISTING_FACEBOOK_USER_ID, name, description, travelMode, null, tipIds);
            RouteDetailsDto oldRouteDetailsDto = routeDetailsDto;

            tipService.remove(cathedralID);
            routeDetailsDto = routeService.findById(routeDetailsDto.getId(), EXISTING_FACEBOOK_USER_ID);

            assertNotNull(routeDetailsDto.getId());
            assertEquals(name, routeDetailsDto.getName());
            assertEquals(description, routeDetailsDto.getDescription());
            assertEquals(travelMode, routeDetailsDto.getTravelMode());
            assertNotNull(routeDetailsDto.getGeom());
            assertTrue(!Objects.equals(routeDetailsDto.getGeom(), oldRouteDetailsDto.getGeom()));
            assertNotNull(routeDetailsDto.getGoogleMapsUrl());
            assertTrue(!Objects.equals(routeDetailsDto.getGoogleMapsUrl(), oldRouteDetailsDto.getGoogleMapsUrl()));
            assertEquals(2, routeDetailsDto.getTips().size());
            assertEquals(EXISTING_FACEBOOK_USER_ID, routeDetailsDto.getCreator().getFacebookUserId());
        } catch (InvalidRouteException e) {
            fail();
        }
    }
}
