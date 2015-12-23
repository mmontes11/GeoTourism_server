package com.mmontes.test.model.service;

import com.mmontes.model.dao.TIPDao;
import com.mmontes.model.entity.TIP.TIP;
import com.mmontes.model.service.FavouriteService;
import com.mmontes.model.service.TIPService;
import com.mmontes.util.GeometryConversor;
import com.mmontes.util.dto.TIPDetailsDto;
import com.mmontes.util.exception.InstanceNotFoundException;
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
public class FavouriteServiceTest {

    @Autowired
    private FavouriteService favouriteService;

    @Autowired
    private TIPDao tipDao;

    @Autowired
    private TIPService tipService;

    @Test
    public void favouriteCreateDeleteTest() {
        try {
            Point geom = (Point) GeometryConversor.geometryFromWKT(POINT_TORRE_HERCULES);
            TIPDetailsDto towerHercules = tipService.create(MONUMENT_DISCRIMINATOR, "Tower of Hercules", "Human Patrimony", VALID_TIP_PHOTO_URL, null, geom);
            TIP tip = tipDao.findById(towerHercules.getId());

            assertEquals(0, tip.getFavouritedBy().size());

            favouriteService.markAsFavourite(tip.getId(), EXISTING_FACEBOOK_USER_ID);

            assertTrue(favouriteService.isFavourite(tip.getId(), EXISTING_FACEBOOK_USER_ID));
            assertEquals(1, tip.getFavouritedBy().size());

            favouriteService.markAsFavourite(tip.getId(), EXISTING_FACEBOOK_USER_ID2);

            assertTrue(favouriteService.isFavourite(tip.getId(), EXISTING_FACEBOOK_USER_ID2));
            assertEquals(2, tip.getFavouritedBy().size());

            favouriteService.deleteFavourite(tip.getId(), EXISTING_FACEBOOK_USER_ID2);

            assertFalse(favouriteService.isFavourite(tip.getId(), EXISTING_FACEBOOK_USER_ID2));
            assertEquals(1, tip.getFavouritedBy().size());

            favouriteService.deleteFavourite(tip.getId(), EXISTING_FACEBOOK_USER_ID);

            assertFalse(favouriteService.isFavourite(tip.getId(), EXISTING_FACEBOOK_USER_ID));
            assertEquals(0, tip.getFavouritedBy().size());
        } catch (Exception e) {
            fail();
        }
    }

    @Test(expected = InstanceNotFoundException.class)
    public void favouriteNonExistingTIP() throws InstanceNotFoundException {
        favouriteService.markAsFavourite(NON_EXISTING_TIP_ID, EXISTING_FACEBOOK_USER_ID);
    }

    @Test(expected = InstanceNotFoundException.class)
    public void deleteFavouriteNonExistingTIP() throws InstanceNotFoundException {
        favouriteService.deleteFavourite(NON_EXISTING_TIP_ID, EXISTING_FACEBOOK_USER_ID);
    }

    @Test(expected = InstanceNotFoundException.class)
    public void favouriteNonExistingUser() throws InstanceNotFoundException {
        TIP tip = null;
        try {
            Point geom = (Point) GeometryConversor.geometryFromWKT(POINT_TORRE_HERCULES);
            TIPDetailsDto towerHercules = tipService.create(MONUMENT_DISCRIMINATOR, "Tower of Hercules", "Human Patrimony", VALID_TIP_PHOTO_URL, null, geom);
            tip = tipDao.findById(towerHercules.getId());
        } catch (Exception e) {
            fail();
        }
        favouriteService.markAsFavourite(tip.getId(), NON_EXISTING_FACEBOOK_USER_ID);
    }

    @Test(expected = InstanceNotFoundException.class)
    public void deleteFavouriteNonExistingUser() throws InstanceNotFoundException {
        TIP tip = null;
        try {
            Point geom = (Point) GeometryConversor.geometryFromWKT(POINT_TORRE_HERCULES);
            TIPDetailsDto towerHercules = tipService.create(MONUMENT_DISCRIMINATOR, "Tower of Hercules", "Human Patrimony", VALID_TIP_PHOTO_URL, null, geom);
            tip = tipDao.findById(towerHercules.getId());
        } catch (Exception e) {
            fail();
        }
        favouriteService.deleteFavourite(tip.getId(), NON_EXISTING_FACEBOOK_USER_ID);
    }
}
