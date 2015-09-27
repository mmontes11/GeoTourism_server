package com.mmontes.model.dao;

import com.mmontes.model.entity.TIP.*;
import com.mmontes.model.util.dao.GenericDaoHibernate;
import com.vividsolutions.jts.geom.Geometry;
import org.hibernate.Criteria;
import org.hibernate.spatial.criterion.SpatialRestrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mmontes.util.Constants.*;

@Repository("TIPDao")
public class TIPDaoHibernate extends GenericDaoHibernate<TIP, Long> implements TIPDao {

    public List<TIP> find(Geometry location, String type, Long cityId, List<Long> facebookUserIds, Double radius) {
        Criteria criteria = null;
        if (type == null) {
            criteria = getSession().createCriteria(TIP.class);
        }else{
            if (type.equals(MONUMENT_DISCRIMINATOR)){
                criteria = getSession().createCriteria(Monument.class);
            }
            if (type.equals(NATURAL_SPACE_DISCRIMINATOR)){
                criteria = getSession().createCriteria(NaturalSpace.class);
            }
            if (type.equals(HOTEL_DISCRIMINATOR)){
                criteria = getSession().createCriteria(Hotel.class);
            }
            if (type.equals(RESTAURANT_DISCRIMINATOR)){
                criteria = getSession().createCriteria(Restaurant.class);
            }
        }
        if (location != null) {
            double r = radius != null? radius : SEARCH_RADIUS_METRES;
            location.setSRID(SRID_DWITHIN);
            criteria.add(SpatialRestrictions.havingSRID("geom",SRID_DWITHIN));
            criteria.add(SpatialRestrictions.distanceWithin("geom",location,1000000));
        }
        return criteria.list();
    }

    public void markAsFavourite(Long TIPId, Long facebookUserId) {

    }
}
