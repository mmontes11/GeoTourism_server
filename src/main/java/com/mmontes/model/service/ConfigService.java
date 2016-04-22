package com.mmontes.model.service;

import com.mmontes.model.entity.OSM.OSMType;
import com.mmontes.util.dto.ConfigDto;
import com.mmontes.util.dto.IDnameDto;
import com.mmontes.util.dto.OSMTypeDto;
import com.mmontes.util.dto.TIPReviewDto;
import com.mmontes.util.exception.DuplicateInstanceException;
import com.mmontes.util.exception.InstanceNotFoundException;
import com.vividsolutions.jts.geom.Geometry;

import java.util.List;

public interface ConfigService {
    ConfigDto getConfig(boolean BBoxMin,boolean hasTIPtype);
    String getBBox();
    void upsertConfigBBox(Geometry bbox);
    List<TIPReviewDto> findUnreviewedTIPs();
    List<OSMTypeDto> getOSMtypes(boolean hasTIPtype);
    List<OSMTypeDto> getOSMtypes(Long TIPtypeID) throws InstanceNotFoundException;
    List<String> getOSMKeys();
    List<String> findOSMTypesByOSMKey(String OSMKey);
    OSMType getOSMTypeByKeyValue(String OSMKey, String OSMValue, boolean hasTIPtype) throws InstanceNotFoundException, DuplicateInstanceException;
}
