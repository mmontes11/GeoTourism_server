package com.mmontes.model.service;


import com.mmontes.model.entity.TIPtype;
import com.mmontes.rest.request.TIPPatchRequest;
import com.mmontes.util.dto.TIPDetailsDto;
import com.mmontes.util.dto.TIPSearchDto;
import com.mmontes.util.exception.*;
import com.vividsolutions.jts.geom.Geometry;

import java.util.List;

public interface TIPService {

    TIPDetailsDto create(Long typeId, String name, String description, String photoUrl, String infoUrl, Geometry geom)
            throws TIPLocationException, InvalidTIPUrlException, GoogleMapsServiceException, InstanceNotFoundException;
    TIPDetailsDto findById(Long TIPId) throws InstanceNotFoundException;
    boolean exists(Long TIPId);
    void remove(Long TIPId) throws InstanceNotFoundException;
    List<TIPSearchDto> find(Long facebookUserId, Geometry location, List<Long> typeIds, List<Long> cityIds, Integer favouritedBy);
    TIPDetailsDto edit(Long TIPId, TIPPatchRequest newData) throws InstanceNotFoundException;
    List<TIPtype> findAllTypes();
}
