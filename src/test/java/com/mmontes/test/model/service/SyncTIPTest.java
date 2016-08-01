package com.mmontes.test.model.service;

import com.mmontes.model.dao.TIPDao;
import com.mmontes.model.entity.TIP.TIP;
import com.mmontes.model.service.TIPService;
import com.mmontes.util.GeometryUtils;
import com.mmontes.util.dto.FeatureSearchDto;
import com.mmontes.util.dto.TIPSyncDto;
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
public class SyncTIPTest {

    private final static Long CATHEDRAL_OSM_ID = 1L;
    private final static Long ALAMEDA_OSM_ID = 2L;
    private final static Long LIBERTY_STATUE_OSM_ID = 3L;

    @Autowired
    private TIPService tipService;
    @Autowired
    private TIPDao tipDao;

    @Before
    public void createTIPs() {
        try {
            Geometry geom = GeometryUtils.geometryFromWKT(POINT_TORRE_HERCULES);
            tipService.create(MONUMENT_DISCRIMINATOR, "Tower of Hercules", "Human Patrimony", VALID_TIP_PHOTO_URL, VALID_TIP_INFO_URL, geom, null, true);
            geom = GeometryUtils.geometryFromWKT(POINT_CATEDRAL_SANTIAGO);
            tipService.create(MONUMENT_DISCRIMINATOR, "Catedral Santiago de Compostela", "Sitio de peregrinacion", VALID_TIP_PHOTO_URL, VALID_TIP_INFO_URL, geom, CATHEDRAL_OSM_ID, true);
            tipService.create(NATURAL_SPACE_DISCRIMINATOR, "Alameda Santiago de Compostela", "Sitio verde", VALID_TIP_PHOTO_URL, VALID_TIP_INFO_URL, geom, ALAMEDA_OSM_ID, true);
            geom = GeometryUtils.geometryFromWKT(POINT_STATUE_OF_LIBERTRY);
            tipService.create(MONUMENT_DISCRIMINATOR, "Liberty Statue", "NY symbol", VALID_TIP_PHOTO_URL, VALID_TIP_INFO_URL, geom, LIBERTY_STATUE_OSM_ID, true);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void syncTIPs() {
        List<FeatureSearchDto> tipDtos;
        try {
            tipDtos = tipService.find(null, null, null, null, null, true, null);
            assertEquals(4, tipDtos.size());

            List<TIPSyncDto> tipSyncDtos = new ArrayList<>();
            TIPSyncDto cathedralTIPSyncDto = new TIPSyncDto();
            cathedralTIPSyncDto.setOsm_id(CATHEDRAL_OSM_ID);
            cathedralTIPSyncDto.setName("Cathedral New Name");
            cathedralTIPSyncDto.setTip_type_id(MONUMENT_DISCRIMINATOR);
            cathedralTIPSyncDto.setInfo_url(VALID_TIP_PHOTO_URL);
            cathedralTIPSyncDto.setPhoto_url(VALID_TIP_PHOTO_URL);
            cathedralTIPSyncDto.setLon(VALID_LONGITUDE);
            cathedralTIPSyncDto.setLat(VALID_LATITUDE);
            tipSyncDtos.add(cathedralTIPSyncDto);

            tipService.syncTIPs(tipSyncDtos);

            tipDtos = tipService.find(null, null, null, null, null, true, null);
            assertEquals(2, tipDtos.size());

            TIP tip = tipDao.findByOSMId(CATHEDRAL_OSM_ID);
            assertEquals(tip.getName(), cathedralTIPSyncDto.getName());
            assertEquals(tip.getInfoUrl(), cathedralTIPSyncDto.getInfo_url());

            TIPSyncDto libertyStatueTIPSyncDto = new TIPSyncDto();
            libertyStatueTIPSyncDto.setOsm_id(LIBERTY_STATUE_OSM_ID);
            libertyStatueTIPSyncDto.setName("Liberty Statue New Name");
            libertyStatueTIPSyncDto.setTip_type_id(MONUMENT_DISCRIMINATOR);
            libertyStatueTIPSyncDto.setInfo_url(VALID_TIP_PHOTO_URL);
            libertyStatueTIPSyncDto.setPhoto_url(VALID_TIP_PHOTO_URL);
            libertyStatueTIPSyncDto.setLon(VALID_LONGITUDE);
            libertyStatueTIPSyncDto.setLat(VALID_LATITUDE);
            tipSyncDtos.add(libertyStatueTIPSyncDto);

            tipService.syncTIPs(tipSyncDtos);

            tipDtos = tipService.find(null, null, null, null, null, true, null);
            assertEquals(3, tipDtos.size());

            tip = tipDao.findByOSMId(LIBERTY_STATUE_OSM_ID);
            assertEquals(tip.getName(), libertyStatueTIPSyncDto.getName());
            assertEquals(tip.getInfoUrl(), libertyStatueTIPSyncDto.getInfo_url());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
