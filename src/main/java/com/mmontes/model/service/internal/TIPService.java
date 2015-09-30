package com.mmontes.model.service.internal;


import com.mmontes.util.dto.TIPDto;
import com.mmontes.util.exception.AmazonServiceExeption;
import com.mmontes.util.exception.InvalidTIPUrlException;
import com.mmontes.util.exception.TIPLocationException;
import com.mmontes.util.exception.WikipediaServiceException;
import com.vividsolutions.jts.geom.Geometry;

import java.util.List;

public interface TIPService {

    TIPDto create(String type, String name, String description, String photoUrl, String photoContent, String extension, String infoUrl, Geometry geom)
            throws AmazonServiceExeption, TIPLocationException, WikipediaServiceException, InvalidTIPUrlException;

    void edit(Long TIPId, String type, String name, String description, String photoUrl, String infoUrl, Geometry geom);

    TIPDto findById(Long TIPId);

    boolean exists(Long TIPId);

    void remove(Long TIPId);

    List<TIPDto> find(Long facebookUserId, Geometry location, String type, Long cityId, Integer favouritedBy, Double radius);
}
