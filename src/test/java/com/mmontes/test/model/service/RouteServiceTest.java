package com.mmontes.test.model.service;

import com.mmontes.model.service.RouteService;
import com.mmontes.model.service.TIPService;
import com.mmontes.util.GeometryUtils;
import com.mmontes.util.dto.RouteDetailsDto;
import com.mmontes.util.dto.TIPDetailsDto;
import com.mmontes.util.dto.TIPMinDto;
import com.mmontes.util.exception.*;
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
public class RouteServiceTest {

    @Autowired
    private RouteService routeService;

    @Autowired
    private TIPService tipService;

    private static Long alamedaID;
    private static Long cathedralID;
    private static Long towerOfHerculesID;
    private static Long reisCatolicosID;
    private static Long statueOfLibertyID;
    private static List<Long> tipIds = new ArrayList<>();
    private static Long routeID;

    @Before
    public void createData(){
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

            name = "Liberty Statue";
            description = "NY symbol";
            geom = GeometryUtils.geometryFromWKT(POINT_STATUE_OF_LIBERTRY);
            tipDetailsDto = tipService.create(MONUMENT_DISCRIMINATOR, name, description, VALID_TIP_PHOTO_URL, null, geom);
            statueOfLibertyID = tipDetailsDto.getId();

            name = "From Alameda To Cathedral";
            description = "Santiago route";
            String travelMode = "driving";
            tipIds.clear();
            tipIds.add(alamedaID);
            tipIds.add(cathedralID);
            tipIds.add(towerOfHerculesID);
            RouteDetailsDto routeDetailsDto = routeService.createRoute(name, description, travelMode, null, tipIds, EXISTING_FACEBOOK_USER_ID);
            routeID = routeDetailsDto.getId();
        } catch (Exception e){
            fail();
        }
    }

    @Test
    public void retrieveTipsInOrder() {
        try {
            List<TIPMinDto> tipMinDtos = routeService.getTIPsInOrder(routeID);
            for (int i = 0; i < tipIds.size(); i++) {
                Long tipId = tipIds.get(i);
                TIPMinDto tipIds = tipMinDtos.get(i);
                assertEquals(tipId, tipIds.getId());
            }
        } catch (InstanceNotFoundException e) {
            fail();
        }
    }

    @Test
    public void createRouteFromTIPs() {
        try {
            String name = "From Alameda To Cathedral";
            String description = "Santiago route";
            String travelMode = "driving";
            List<Long> tipdIds = new ArrayList<Long>() {{
                add(alamedaID);
                add(cathedralID);
                add(towerOfHerculesID);
            }};
            RouteDetailsDto routeDetailsDto = routeService.createRoute(name, description, travelMode, null, tipdIds, EXISTING_FACEBOOK_USER_ID);

            assertNotNull(routeDetailsDto.getId());
            assertEquals(name, routeDetailsDto.getName());
            assertEquals(description, routeDetailsDto.getDescription());
            assertEquals(travelMode, routeDetailsDto.getTravelMode());
            assertNotNull(routeDetailsDto.getGeom());
            assertNotNull(routeDetailsDto.getGoogleMapsUrl());
            assertEquals(3, routeDetailsDto.getTips().size());
            assertEquals(EXISTING_FACEBOOK_USER_ID, routeDetailsDto.getCreator().getFacebookUserId());
        } catch (Exception e) {
            fail();
        }
    }
}
