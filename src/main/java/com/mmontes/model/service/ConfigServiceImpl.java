package com.mmontes.model.service;

import com.mmontes.model.dao.*;
import com.mmontes.model.entity.Config;
import com.mmontes.model.entity.OSM.OSMType;
import com.mmontes.model.entity.TIP.TIP;
import com.mmontes.model.entity.TIP.TIPtype;
import com.mmontes.util.GeometryUtils;
import com.mmontes.util.dto.ConfigDto;
import com.mmontes.util.dto.DtoService;
import com.mmontes.util.dto.OSMTypeDto;
import com.mmontes.util.dto.TIPReviewDto;
import com.mmontes.util.exception.DuplicateInstanceException;
import com.mmontes.util.exception.InstanceNotFoundException;
import com.vividsolutions.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    private TIPtypeDao tipTypeDao;

    @Autowired
    private DtoService dtoService;

    @Autowired
    private TIPDao tipDao;

    @Override
    public ConfigDto getConfig(boolean BBoxMin, boolean hasTIPtype) {
        ConfigDto configDto = new ConfigDto();
        Config config = configDao.getConfig();
        String bbox;
        System.out.println(config.getBoundingBox());
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
    public List<TIPReviewDto> findUnreviewedTIPs() {
        List<TIP> tips = tipDao.find(null,null,null,null,null,false,null);
        return dtoService.ListTIP2ListTIPReviewDto(tips);
    }

    @Override
    public List<OSMTypeDto> getOSMtypes(boolean hasTIPtype) {
        List<OSMType> osmTypes = osmTypeDao.find(hasTIPtype);
        return dtoService.ListOSMType2ListOSMTypeDto(osmTypes);
    }

    @Override
    public List<OSMTypeDto> getOSMtypes(Long TIPtypeID) throws InstanceNotFoundException {
        TIPtype tipType = tipTypeDao.findById(TIPtypeID);
        return dtoService.ListOSMType2ListOSMTypeDto(new ArrayList<>(tipType.getOsmTypes()));
    }

    @Override
    public List<String> getOSMKeys() {
        return osmKeyDao.findAll();
    }

    @Override
    public List<String> findOSMTypesByOSMKey(String OSMKey) {
        return osmTypeDao.findByKey(OSMKey);
    }

    @Override
    public void checkUnmappedOSMtype(String OSMKey, String OSMValue) throws DuplicateInstanceException, InstanceNotFoundException {
        OSMType osmType = osmTypeDao.findByKeyValue(OSMKey,OSMValue);
        if (osmType.getTipType() != null){
            throw new DuplicateInstanceException(OSMType.class.getName(),OSMValue);
        }
    }

    @Override
    public OSMType getOSMTypeByKeyValue(String OSMKey, String OSMValue) throws InstanceNotFoundException {
        return osmTypeDao.findByKeyValue(OSMKey,OSMValue);
    }
}
