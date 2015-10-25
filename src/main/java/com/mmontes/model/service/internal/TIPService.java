package com.mmontes.model.service.internal;


import com.mmontes.rest.request.TIPPatchRequest;
import com.mmontes.util.dto.TIPDto;
import com.mmontes.util.exception.*;
import com.vividsolutions.jts.geom.Geometry;

import java.util.List;

public interface TIPService {

    TIPDto create(String type, String name, String description, String photoUrl, String photoContent, String photoName, String infoUrl, Geometry geom)
            throws AmazonServiceExeption, TIPLocationException, WikipediaServiceException, InvalidTIPUrlException, GoogleMapsServiceException;

    TIPDto findById(Long TIPId) throws InstanceNotFoundException;

    boolean exists(Long TIPId);

    void remove(Long TIPId) throws InstanceNotFoundException;

    List<TIPDto> find(Long facebookUserId, Geometry location, String type, Long cityId, Integer favouritedBy, Double radius);

    void edit(Long TIPId, TIPPatchRequest newData) throws InstanceNotFoundException, AmazonServiceExeption;
}
