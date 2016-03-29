package com.mmontes.model.service;

import com.mmontes.model.dao.ConfigDao;
import com.mmontes.model.dao.OSMKeyDao;
import com.mmontes.model.dao.OSMTypeDao;
import com.mmontes.model.dao.OSMValueDao;
import com.mmontes.model.entity.Config;
import com.mmontes.model.entity.OSM.OSMKey;
import com.mmontes.model.entity.OSM.OSMType;
import com.mmontes.model.entity.OSM.OSMValue;
import com.mmontes.util.GeometryUtils;
import com.mmontes.util.dto.ConfigDto;
import com.mmontes.util.dto.DtoService;
import com.mmontes.util.dto.IDnameDto;
import com.mmontes.util.dto.OSMTypeDto;
import com.mmontes.util.exception.InstanceNotFoundException;
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
    private OSMKeyDao osmKeyDao;

    @Autowired
    private OSMValueDao osmValueDao;

    @Autowired
    private DtoService dtoService;

    @Override
    public ConfigDto getConfig() {
        ConfigDto configDto = new ConfigDto();
        Config config = configDao.getConfig();
        String bbox = GeometryUtils.getBBoxString(config.getBoundingBox());
        configDto.setBbox(bbox);
        List<OSMType> osmTypes = osmTypeDao.getOSMTypes(true);
        configDto.setOsmTypes(dtoService.ListOSMType2ListOSMTypeDto(osmTypes));
        return configDto;
    }

    @Override
    public String getBBox() {
        Config config = configDao.getConfig();
        return GeometryUtils.WKTFromGeometry(config.getBoundingBox());
    }

    @Override
    public List<OSMTypeDto> getOSMTypes(Boolean tipTypeSetted) {
        List<OSMType> osmTypes = osmTypeDao.getOSMTypes(tipTypeSetted);
        return dtoService.ListOSMType2ListOSMTypeDto(osmTypes);
    }

    @Override
    public List<IDnameDto> getOSMKeys() {
        return dtoService.ListOSMKey2ListIDnameDto(osmKeyDao.findAll());
    }

    @Override
    public List<IDnameDto> findOSMValuesByOSMKey(String OSMKey){
        return dtoService.ListOSMValue2ListIDnameDto(osmValueDao.findOSMValuesByOSMKey(OSMKey));
    }
}
