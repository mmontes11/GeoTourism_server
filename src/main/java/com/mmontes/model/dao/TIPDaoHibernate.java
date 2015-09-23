package com.mmontes.model.dao;

import com.mmontes.model.entity.TIP.TIP;
import com.mmontes.model.util.dao.GenericDaoHibernate;
import com.vividsolutions.jts.geom.Geometry;
import org.hibernate.Criteria;
import org.hibernate.spatial.criterion.SpatialRestrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mmontes.util.GlobalNames.SEARCH_RADIUS_METRES;

@Repository("TIPDao")
public class TIPDaoHibernate extends GenericDaoHibernate<TIP, Long> implements TIPDao {

    public List<TIP> find(Geometry location, String type, Long cityId, List<Long> facebookUserIds) {
        Criteria criteria = null;
        if (type == null) {
            criteria = getSession().createCriteria(TIP.class);
        }
        if (location != null) {
            criteria.add(SpatialRestrictions.distanceWithin("geom", location, SEARCH_RADIUS_METRES));
        }
        return criteria.list();
    }

    public void markAsFavourite(Long TIPId, Long facebookUserId) {

    }
}
