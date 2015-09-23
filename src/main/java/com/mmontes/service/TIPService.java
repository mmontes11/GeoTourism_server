package com.mmontes.service;


import com.mmontes.util.dto.TIPDto;
import org.postgis.Geometry;

import java.util.List;

public interface TIPService {

    void create (Long type,String description, String photoUrl, String infoUrl, Geometry geom);
    void edit(Long TIPId, String type, String name, String description, String photoUrl, String infoUrl, Geometry geom);
    TIPDto findById(Long TIPId);
    boolean exists(Long TIPId);
    void remove(Long TIPId);
    List<TIPDto> find(Long facebookUserId, Geometry location, String type, Long cityId, Integer favouritedBy);
}
