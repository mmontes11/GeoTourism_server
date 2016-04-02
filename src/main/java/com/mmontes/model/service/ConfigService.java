package com.mmontes.model.service;

import com.mmontes.model.entity.OSM.OSMKey;
import com.mmontes.model.entity.OSM.OSMValue;
import com.mmontes.util.dto.ConfigDto;
import com.mmontes.util.dto.IDnameDto;
import com.mmontes.util.dto.OSMTypeDto;
import com.mmontes.util.exception.DuplicateInstanceException;
import com.mmontes.util.exception.InstanceNotFoundException;

import java.util.List;

public interface ConfigService {
    ConfigDto getConfig(boolean BBoxMin);
    OSMTypeDto createOSMType(Long osmValueId,Long tipTypeId) throws InstanceNotFoundException, DuplicateInstanceException;
    OSMTypeDto updateOSMType(Long osmTypeId,Long tipTypeId) throws InstanceNotFoundException;
    void deleteOSMType(Long osmValueId) throws InstanceNotFoundException;
    List<IDnameDto> getOSMKeys();
    List<IDnameDto> findOSMValuesByOSMKey(String OSMKey);
}
