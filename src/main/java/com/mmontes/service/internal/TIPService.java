package com.mmontes.service.internal;


import com.mmontes.model.entity.TIP.TIP;
import com.mmontes.util.dto.TIPDto;
import com.vividsolutions.jts.geom.Geometry;

import java.util.List;

public interface TIPService {

    TIP create(String type, String name, String description, String photoUrl, String photoContent, String infoUrl, String geom);
    void edit(Long TIPId, String type, String name, String description, String photoUrl, String infoUrl, Geometry geom);
    TIPDto findById(Long TIPId);
    boolean exists(Long TIPId);
    void remove(Long TIPId);
    List<TIPDto> find(Long facebookUserId, Geometry location, String type, Long cityId, Integer favouritedBy);
}
