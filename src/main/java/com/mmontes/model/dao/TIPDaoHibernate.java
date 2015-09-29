package com.mmontes.model.dao;

import com.mmontes.model.entity.TIP.*;
import com.mmontes.model.util.dao.GenericDaoHibernate;
import com.vividsolutions.jts.geom.Geometry;
import org.hibernate.Criteria;
import org.hibernate.spatial.criterion.SpatialRestrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mmontes.util.Constants.*;

@Repository("TIPDao")
public class TIPDaoHibernate extends GenericDaoHibernate<TIP, Long> implements TIPDao {

    public List<TIP> find(Geometry location, String type, Long cityId, List<Long> facebookUserIds, Double radius) {
        String queryString = "SELECT tip ";
        if (type == null) {
            queryString += "FROM TIP tip ";
        }else{
            if (type.equals(MONUMENT_DISCRIMINATOR)){
                queryString += "FROM Monument tip ";
            }
            if (type.equals(NATURAL_SPACE_DISCRIMINATOR)){
                queryString += "FROM NaturalSpace tip ";
            }
            if (type.equals(HOTEL_DISCRIMINATOR)){
                queryString += "FROM Hotel tip " ;
            }
            if (type.equals(RESTAURANT_DISCRIMINATOR)){
                queryString += "FROM Restaurant tip ";
            }
        }
        queryString += "WHERE dwithin(transform(tip.geom,:srid), transform(:location,:srid), :radius) = true ";

        double r = radius != null? radius : SEARCH_RADIUS_METRES;
        return getSession()
                .createQuery(queryString)
                .setParameter("srid",SRID_DWITHIN)
                .setParameter("location",location)
                .setParameter("radius",r)
                .list();
    }

    public void markAsFavourite(Long TIPId, Long facebookUserId) {

    }
}
