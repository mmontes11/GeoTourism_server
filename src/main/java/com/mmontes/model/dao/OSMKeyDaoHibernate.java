package com.mmontes.model.dao;


import com.mmontes.model.entity.OSM.OSMKey;
import com.mmontes.model.util.genericdao.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("OSMKeyDao")
@SuppressWarnings("all")
public class OSMKeyDaoHibernate extends GenericDaoHibernate<OSMKey,Long> implements OSMKeyDao{
    @Override
    public List<String> findAll() {
        String queryString = "SELECT o.key FROM OSMKey o";
        return (List<String>)
                getSession()
                    .createQuery(queryString)
                    .list();
    }
}
