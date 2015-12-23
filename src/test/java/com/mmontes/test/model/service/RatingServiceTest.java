package com.mmontes.test.model.service;

import com.mmontes.model.dao.TIPDao;
import com.mmontes.model.entity.TIP.TIP;
import com.mmontes.model.service.RatingService;
import com.mmontes.model.service.TIPService;
import com.mmontes.util.GeometryConversor;
import com.mmontes.util.dto.TIPDetailsDto;
import com.vividsolutions.jts.geom.Point;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static com.mmontes.test.util.Constants.*;
import static com.mmontes.util.Constants.SPRING_CONFIG_FILE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE})
@Transactional
public class RatingServiceTest {

    @Autowired
    private RatingService ratingService;

    @Autowired
    private TIPDao tipDao;

    @Autowired
    private TIPService tipService;

    @Test
    public void rateAndGetAverage() {
        try {
            Point geom = (Point) GeometryConversor.geometryFromWKT(POINT_TORRE_HERCULES);
            TIPDetailsDto towerHercules = tipService.create(MONUMENT_DISCRIMINATOR, "Tower of Hercules", "Human Patrimony", VALID_TIP_PHOTO_URL, null, geom);
            TIP tip = tipDao.findById(towerHercules.getId());

            Double average = ratingService.rate(10.0D, tip.getId(), EXISTING_FACEBOOK_USER_ID);
            assertEquals(Double.valueOf(10.0D), average);
            assertEquals(Double.valueOf(10.0D), ratingService.getAverageRate(tip.getId()));

            average = ratingService.rate(5.0D, tip.getId(), EXISTING_FACEBOOK_USER_ID);
            assertEquals(Double.valueOf(5.0D), average);
            assertEquals(Double.valueOf(5.0D), ratingService.getAverageRate(tip.getId()));

            average = ratingService.rate(10.0D, tip.getId(), EXISTING_FACEBOOK_USER_ID2);
            assertEquals(Double.valueOf(7.5D), average);
            assertEquals(Double.valueOf(7.5D), ratingService.getAverageRate(tip.getId()));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void rateAndGetUserRating() {
        try {
            Point geom = (Point) GeometryConversor.geometryFromWKT(POINT_TORRE_HERCULES);
            TIPDetailsDto towerHercules = tipService.create(MONUMENT_DISCRIMINATOR, "Tower of Hercules", "Human Patrimony", VALID_TIP_PHOTO_URL, null, geom);
            TIP tip = tipDao.findById(towerHercules.getId());

            Double rate = 10.0D;
            ratingService.rate(rate, tip.getId(), EXISTING_FACEBOOK_USER_ID);
            assertEquals(rate, ratingService.getUserTIPRate(tip.getId(),EXISTING_FACEBOOK_USER_ID));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
