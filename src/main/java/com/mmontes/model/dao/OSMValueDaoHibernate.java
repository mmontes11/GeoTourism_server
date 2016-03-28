package com.mmontes.model.dao;

import com.mmontes.model.entity.OSM.OSMValue;
import com.mmontes.model.util.genericdao.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("OSMValueDao")
@SuppressWarnings("all")
public class OSMValueDaoHibernate extends GenericDaoHibernate<OSMValue, Long> implements OSMValueDao {

    @Override
    public List<OSMValue> findOSMValuesByOSMKey(String OSMKey) {
        String queryString = "SELECT o FROM OSMValue WHERE o.key = :OSMKey";
        return (List<OSMValue>)
                getSession()
                        .createQuery(queryString)
                        .setParameter("OSMKey", OSMKey)
                        .list();
    }
}
