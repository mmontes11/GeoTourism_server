package com.mmontes.model.dao;

import com.mmontes.model.entity.City;
import com.mmontes.model.util.genericdao.GenericDaoHibernate;
import com.vividsolutions.jts.geom.Geometry;
import org.springframework.stereotype.Repository;

@Repository("cityDao")
public class CityDaoHibernate extends GenericDaoHibernate<City,Long> implements CityDao{

    public City getCityFromLocation(Geometry location) {
        String queryString = "SELECT c FROM City c WHERE within(:location,c.geom) = true";
        return (City) getSession()
                        .createQuery(queryString)
                        .setParameter("location", location)
                        .uniqueResult();
    }
}
