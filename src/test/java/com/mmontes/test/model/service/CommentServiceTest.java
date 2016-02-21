package com.mmontes.test.model.service;

import com.mmontes.model.dao.TIPDao;
import com.mmontes.model.entity.TIP.TIP;
import com.mmontes.model.service.CommentService;
import com.mmontes.model.service.TIPService;
import com.mmontes.util.GeometryUtils;
import com.mmontes.util.dto.CommentDto;
import com.mmontes.util.dto.TIPDetailsDto;
import com.mmontes.util.exception.InstanceException;
import com.mmontes.util.exception.InstanceNotFoundException;
import com.vividsolutions.jts.geom.Point;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mmontes.test.util.Constants.*;
import static com.mmontes.util.Constants.SPRING_CONFIG_FILE;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE})
@Transactional
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private TIPDao tipDao;

    @Autowired
    private TIPService tipService;

    @Test
    public void addDeleteComments() {
        try {
            Point geom = (Point) GeometryUtils.geometryFromWKT(POINT_TORRE_HERCULES);
            TIPDetailsDto towerHercules = tipService.create(MONUMENT_DISCRIMINATOR, "Tower of Hercules", "Human Patrimony", VALID_TIP_PHOTO_URL, null, geom, null, null);
            TIP tip = tipDao.findById(towerHercules.getId());

            List<CommentDto> commentDtos = commentService.comment("Nice", EXISTING_FACEBOOK_USER_ID, tip.getId());
            commentDtos = commentService.comment("Ugly", EXISTING_FACEBOOK_USER_ID, tip.getId());

            assertEquals(2, commentDtos.size());

            commentDtos = commentService.comment("Nice", EXISTING_FACEBOOK_USER_ID2, tip.getId());
            commentDtos = commentService.comment("Ugly", EXISTING_FACEBOOK_USER_ID2, tip.getId());

            assertEquals(4, commentDtos.size());

            commentService.deleteComment(commentDtos.get(0).getId(), tip.getId(), EXISTING_FACEBOOK_USER_ID);

            assertEquals(3, commentDtos.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(expected = InstanceException.class)
    public void deleteInvalidComment() throws InstanceNotFoundException {
        TIP tip = null;
        try {
            Point geom = (Point) GeometryUtils.geometryFromWKT(POINT_TORRE_HERCULES);
            TIPDetailsDto towerHercules = tipService.create(MONUMENT_DISCRIMINATOR, "Tower of Hercules", "Human Patrimony", VALID_TIP_PHOTO_URL, null, geom, null, null);
            tip = tipDao.findById(towerHercules.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<CommentDto> commentDtos = null;
        try {
            commentDtos = commentService.comment("Nice", EXISTING_FACEBOOK_USER_ID,tip.getId());
            assertEquals(1, commentDtos.size());
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
        }
        commentService.deleteComment(commentDtos.get(0).getId(),tip.getId(),EXISTING_FACEBOOK_USER_ID2);
    }
}
