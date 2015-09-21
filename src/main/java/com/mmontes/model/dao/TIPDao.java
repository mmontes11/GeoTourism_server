package com.mmontes.model.dao;

import com.mmontes.model.entity.TIP.TIP;
import com.mmontes.model.util.GenericDao;
import org.postgis.Point;

import java.util.List;

public interface TIPDao extends GenericDao<TIP, Long>{

    List<TIP> find(Point location,String type,Long cityId,List<Long> facebookUserIds);
    void markAsFavourite(Long TIPId, Long facebookUserId);
}
