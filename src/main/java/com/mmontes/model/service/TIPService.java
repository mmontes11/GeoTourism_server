package com.mmontes.model.service;


import com.mmontes.util.dto.FeatureSearchDto;
import com.mmontes.util.dto.TIPDetailsDto;
import com.mmontes.util.dto.TIPRouteDto;
import com.mmontes.util.dto.TIPSyncDto;
import com.mmontes.util.exception.*;
import com.vividsolutions.jts.geom.Geometry;

import java.util.List;

public interface TIPService {

    TIPDetailsDto create(Long typeId, String name, String description, String photoUrl, String infoUrl, Geometry geom, Long cityId, Long osmId)
            throws TIPLocationException, InvalidTIPUrlException, InstanceNotFoundException;
    TIPDetailsDto findById(Long TIPId,Long facebooUserId) throws InstanceNotFoundException;
    TIPRouteDto findById(Long TIPId) throws InstanceNotFoundException;
    void remove(Long TIPId) throws InstanceNotFoundException, InvalidRouteException;
    List<FeatureSearchDto> find(String boundsWKT, List<Long> typeIds, List<Long> cityIds, List<Long> facebookUserIds)
            throws InstanceNotFoundException;
    TIPDetailsDto edit(Long TIPId, Long facebookUserId, Long type, String name, String description, String infoUrl, String address, String photoUrl)
            throws InstanceNotFoundException, InvalidTIPUrlException;
    boolean geometryContainsTIPs(Geometry superGeometry,List<Long> tipIds);
    int getNumRoutes(Long tipId) throws InstanceNotFoundException;
    void syncTIPs(List<TIPSyncDto> tipSyncDtos);
}
