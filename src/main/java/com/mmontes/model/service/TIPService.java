package com.mmontes.model.service;


import com.mmontes.rest.request.TIPPatchRequest;
import com.mmontes.util.dto.TIPDetailsDto;
import com.mmontes.util.dto.TIPSearchDto;
import com.mmontes.util.exception.*;
import com.vividsolutions.jts.geom.Geometry;

import java.util.List;

public interface TIPService {

    TIPDetailsDto create(String type, String name, String description, String photoUrl, String photoContent, String photoName, String infoUrl, Geometry geom)
            throws AmazonServiceExeption, TIPLocationException, WikipediaServiceException, InvalidTIPUrlException, GoogleMapsServiceException;

    TIPDetailsDto findById(Long TIPId) throws InstanceNotFoundException;

    boolean exists(Long TIPId);

    void remove(Long TIPId) throws InstanceNotFoundException;

    List<TIPSearchDto> find(Long facebookUserId, Geometry location, String type, Long cityId, Integer favouritedBy, Double radius);

    TIPDetailsDto edit(Long TIPId, TIPPatchRequest newData) throws InstanceNotFoundException, AmazonServiceExeption;
}
