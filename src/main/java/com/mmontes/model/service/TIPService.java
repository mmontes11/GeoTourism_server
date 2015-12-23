package com.mmontes.model.service;


import com.mmontes.rest.request.TIPPatchRequest;
import com.mmontes.util.dto.TIPDetailsDto;
import com.mmontes.util.dto.FeatureSearchDto;
import com.mmontes.util.exception.*;
import com.vividsolutions.jts.geom.Geometry;

import java.util.List;

public interface TIPService {

    TIPDetailsDto create(Long typeId, String name, String description, String photoUrl, String infoUrl, Geometry geom)
            throws TIPLocationException, InvalidTIPUrlException, GoogleMapsServiceException, InstanceNotFoundException;
    TIPDetailsDto findById(Long TIPId,Long facebooUserId) throws InstanceNotFoundException;
    boolean exists(Long TIPId);
    void remove(Long TIPId) throws InstanceNotFoundException;
    List<FeatureSearchDto> find(Geometry bounds, List<Long> typeIds, List<Long> cityIds, Integer favouritedBy, Long facebookUserId, List<Long> friendsFacebookUserIds)
            throws InstanceNotFoundException;
    TIPDetailsDto edit(Long TIPId, Long facebooUserId, TIPPatchRequest newData) throws InstanceNotFoundException;
}
