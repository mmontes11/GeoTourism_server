package com.mmontes.model.service;

import com.mmontes.model.dao.ConfigDao;
import com.mmontes.model.dao.OSMKeyDao;
import com.mmontes.model.dao.OSMTypeDao;
import com.mmontes.model.dao.TIPDao;
import com.mmontes.model.entity.Config;
import com.mmontes.model.entity.OSM.OSMType;
import com.mmontes.model.entity.TIP.TIP;
import com.mmontes.util.GeometryUtils;
import com.mmontes.util.dto.*;
import com.vividsolutions.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("ConfigService")
@Transactional
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private ConfigDao configDao;

    @Autowired
    private OSMKeyDao osmKeyDao;

    @Autowired
    private OSMTypeDao osmTypeDao;

    @Autowired
    private DtoService dtoService;

    @Autowired
    private TIPDao tipDao;

    @Override
    public ConfigDto getConfig(boolean BBoxMin, boolean hasTIPtype) {
        ConfigDto configDto = new ConfigDto();
        Config config = configDao.getConfig();
        String bbox;
        if (BBoxMin) {
            bbox = GeometryUtils.getBBoxString(config.getBoundingBox());
        } else {
            bbox = GeometryUtils.WKTFromGeometry(config.getBoundingBox());
        }
        configDto.setBbox(bbox);
        List<OSMType> osmTypes = osmTypeDao.find(hasTIPtype);
        configDto.setOsmTypes(dtoService.ListOSMType2ListOSMTypeDto(osmTypes));
        return configDto;
    }

    @Override
    public String getBBox() {
        Config config = configDao.getConfig();
        return GeometryUtils.WKTFromGeometry(config.getBoundingBox());
    }

    @Override
    public void upsertConfigBBox(Geometry bbox) {
        Config config = configDao.getConfig();
        if (config == null) {
            config = new Config();
        }
        config.setBoundingBox(bbox);
        configDao.save(config);
    }

    @Override
    public List<OSMTypeDto> getOSMtypes() {
        List<OSMType> osmTypes = osmTypeDao.find(true);
        return dtoService.ListOSMType2ListOSMTypeDto(osmTypes);
    }

    @Override
    public List<String> getOSMKeys() {
        return osmKeyDao.findAll();
    }

    @Override
    public List<String> findOSMTypesByOSMKey(String OSMKey) {
        return osmTypeDao.findOSMTypeByOSMKey(OSMKey);
    }

    @Override
    public List<TIPReviewDto> findUnreviewedTIPs() {
        List<TIP> tips = tipDao.find(null,null,null,null,null,false);
        return dtoService.ListTIP2ListTIPReviewDto(tips);
    }
}
