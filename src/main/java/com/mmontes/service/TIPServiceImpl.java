package com.mmontes.service;

import com.mmontes.util.dto.TIPDto;
import org.postgis.Geometry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("TIPService")
@Transactional
public class TIPServiceImpl implements TIPService {
    public void create(Long type, String description, String photoUrl, String infoUrl, Geometry geom) {

    }

    public void edit(Long TIPId, String type, String name, String description, String photoUrl, String infoUrl, Geometry geom) {

    }

    public TIPDto findById(Long TIPId) {
        return null;
    }

    public boolean exists(Long TIPId) {
        return false;
    }

    public void remove(Long TIPId) {

    }

    public List<TIPDto> find(Long facebookUserId, Geometry location, String type, Long cityId, Integer favouritedBy) {
        return null;
    }
}
