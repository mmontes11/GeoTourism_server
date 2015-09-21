package com.mmontes.model.dao;

import com.mmontes.model.entity.TIP.TIP;
import com.mmontes.model.util.GenericDaoHibernate;
import org.postgis.Point;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("TIPDao")
public class TIPDaoHibernate extends GenericDaoHibernate<TIP, Long> implements TIPDao {

    public List<TIP> find(Point location, String type, Long cityId, List<Long> facebookUserIds) {
        return null;
    }

    public void markAsFavourite(Long TIPId, Long facebookUserId) {

    }
}
