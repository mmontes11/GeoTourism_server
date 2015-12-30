package com.mmontes.test.model.dao;

import com.mmontes.model.dao.RouteDao;
import com.mmontes.model.entity.TIP.TIP;
import com.mmontes.model.service.RouteService;
import com.mmontes.model.service.TIPService;
import com.mmontes.util.GeometryConversor;
import com.mmontes.util.dto.RouteDetailsDto;
import com.mmontes.util.dto.TIPDetailsDto;
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
@ContextConfiguration(locations = { SPRING_CONFIG_FILE,SPRING_CONFIG_TEST_FILE })
@Transactional
public class RouteDaoTest {

    @Autowired
    private RouteDao routeDao;

    @Autowired
    private RouteService routeService;

    @Autowired
    private TIPService tipService;

    private static List<Long> tipIds = new ArrayList<>();;
    private static Long routeID;

    @Before
    public void createData() throws InvalidTIPUrlException, InstanceNotFoundException, GoogleMapsServiceException, TIPLocationException, GeometryParsingException {
        String name = "Alameda Park";
        String description = "Green zone";
        Geometry geom = GeometryConversor.geometryFromWKT(POINT_ALAMEDA);
        TIPDetailsDto tipDetailsDto = tipService.create(NATURAL_SPACE_DISCRIMINATOR, name, description, VALID_TIP_PHOTO_URL, VALID_TIP_INFO_URL, geom);
        Long alamedaID = tipDetailsDto.getId();

        name = "Santiago de Compostela cathedral";
        description = "Human patrimony";
        geom = GeometryConversor.geometryFromWKT(POINT_CATEDRAL_SANTIAGO);
        tipDetailsDto = tipService.create(MONUMENT_DISCRIMINATOR, name, description, VALID_TIP_PHOTO_URL, VALID_TIP_INFO_URL, geom);
        Long cathedralID = tipDetailsDto.getId();

        name = "Tower of Hercules";
        description = "Human Patrimony";
        geom = GeometryConversor.geometryFromWKT(POINT_TORRE_HERCULES);
        tipDetailsDto = tipService.create(MONUMENT_DISCRIMINATOR, name, description, VALID_TIP_PHOTO_URL, VALID_TIP_INFO_URL, geom);
        Long towerOfHerculesID = tipDetailsDto.getId();

        name = "From Alameda To Cathedral";
        description = "Santiago route";
        String travelMode = "driving";
        tipIds.add(alamedaID);
        tipIds.add(cathedralID);
        tipIds.add(towerOfHerculesID);
        RouteDetailsDto routeDetailsDto = routeService.createRoute(name,description,travelMode,null,tipIds, EXISTING_FACEBOOK_USER_ID);
        routeID = routeDetailsDto.getId();
    }

    @Test
    public void retrieveTipsInOrder(){
        List<TIP> tips = routeDao.getTIPsInOrder(routeID);
        for (int i = 0; i<tipIds.size(); i++){
            Long tipId = tipIds.get(i);
            TIP tip = tips.get(i);
            assertEquals(tipId,tip.getId());
        }
    }

}
