package com.mmontes.model.service;

import com.mmontes.model.dao.ConfigDao;
import com.mmontes.model.dao.OSMTypeDao;
import com.mmontes.model.entity.Config;
import com.mmontes.model.entity.OSMType;
import com.mmontes.util.GeometryUtils;
import com.mmontes.util.dto.ConfigDto;
import com.mmontes.util.dto.DtoService;
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
    private OSMTypeDao osmTypeDao;

    @Autowired
    private DtoService dtoService;

    @Override
    public ConfigDto getConfig() {
        ConfigDto configDto = new ConfigDto();
        Config config = configDao.getConfigBoundingBox();
        configDto.setBbox(GeometryUtils.WKTFromGeometry(config.getBoundingBox()));

        List<OSMType> osmTypes = osmTypeDao.getOSMTypes();
        configDto.setOsmTypes(dtoService.ListOSMType2ListOSMTypeDto(osmTypes));
        return configDto;
    }
}
