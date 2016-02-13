package com.mmontes.model.service;

import com.mmontes.model.dao.ConfigDao;
import com.mmontes.model.dao.OSMTypeDao;
import com.mmontes.model.entity.Config;
import com.mmontes.model.entity.OSMType;
import com.mmontes.util.dto.ConfigDto;
import com.mmontes.util.dto.DtoService;
import com.vividsolutions.jts.geom.Coordinate;
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
    private OSMTypeDao osmTypeDao;

    @Autowired
    private DtoService dtoService;

    @Override
    public ConfigDto getConfig() {
        ConfigDto configDto = new ConfigDto();
        Config config = configDao.getConfig();
        final Coordinate[] coords = config.getBoundingBox().getCoordinates();
        List<Coordinate> bboxCoords = new ArrayList<Coordinate>() {{
            add(coords[0]);
            add(coords[2]);
        }};
        StringBuilder bboxString = new StringBuilder();
        for (int i = 0; i < bboxCoords.size(); i++) {
            Coordinate c = bboxCoords.get(i);
            bboxString.append(c.x).append(",").append(c.y);
            if (i != (bboxCoords.size() - 1)) {
                bboxString.append(",");
            }
        }

        configDto.setBbox(bboxString.toString());
        List<OSMType> osmTypes = osmTypeDao.getOSMTypes();
        configDto.setOsmTypes(dtoService.ListOSMType2ListOSMTypeDto(osmTypes));
        return configDto;
    }
}
