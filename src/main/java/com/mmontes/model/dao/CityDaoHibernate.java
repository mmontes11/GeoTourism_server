package com.mmontes.model.dao;

import com.mmontes.model.entity.City;
import com.mmontes.model.util.QueryUtils;
import com.mmontes.model.util.genericdao.GenericDaoHibernate;
import com.mmontes.util.exception.InstanceNotFoundException;
import com.mmontes.util.exception.TIPLocationException;
import com.vividsolutions.jts.geom.Geometry;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("CityDao")
@SuppressWarnings("unchecked")
public class CityDaoHibernate extends GenericDaoHibernate<City, Long> implements CityDao {

    @Override
    public List<City> getCitiesFromTIPLocation(Geometry location) {
        String queryString = "SELECT c FROM City c WHERE within(:location,c.geom) = true";
        return (List<City>) getSession().createQuery(queryString).setParameter("location", location).list();
    }

    @Override
    public List<City> findAll() {
        String queryString = "SELECT c FROM City c";
        return (List<City>) getSession().createQuery(queryString).list();
    }

    @Override
    public City findByOsmId(Long osmId) throws InstanceNotFoundException {
        String queryString = "SELECT c FROM City c WHERE c.osmId = :osmId";
        City city = (City) getSession().createQuery(queryString).setParameter("osmId", osmId).uniqueResult();
        if (city == null) {
            throw new InstanceNotFoundException(osmId, City.class.getName());
        } else {
            return city;
        }
    }

    @Override
    public List<City> getCityEnvelopes() {
        String queryString = "SELECT id,name,ST_Envelope(geom) AS geom,osmid FROM city";
        return (List<City>) getSession().createSQLQuery(queryString).addEntity(City.class).list();
    }

    @Override
    public void deleteNonExistingCities(List<Long> cityIds) {
        String ids = QueryUtils.getINvalues(cityIds);
        String queryString = "DELETE FROM City c WHERE c.osmId IS NOT NULL AND c.osmId NOT IN "+ids;
        getSession().createSQLQuery(queryString).executeUpdate();
    }
}
