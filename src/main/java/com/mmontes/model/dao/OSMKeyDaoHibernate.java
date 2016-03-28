package com.mmontes.model.dao;


import com.mmontes.model.entity.OSM.OSMKey;
import com.mmontes.model.util.genericdao.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("OSMKeyDao")
@SuppressWarnings("all")
public class OSMKeyDaoHibernate extends GenericDaoHibernate<OSMKey,Long> implements OSMKeyDao{
    @Override
    public List<OSMKey> findAll() {
        String queryString = "SELECT o FROM OSMKey o";
        return (List<OSMKey>)
                getSession()
                    .createQuery(queryString)
                    .list();
    }
}
