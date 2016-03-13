package com.mmontes.model.dao;

import com.mmontes.model.entity.OSMType;
import com.mmontes.model.util.genericdao.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("OSMTypeDao")
@SuppressWarnings("all")
public class OSMTypeDaoHibernate extends GenericDaoHibernate<OSMType,Long> implements OSMTypeDao {
    @Override
    public List<OSMType> getAllOSMTypes() {
        String queryString = "SELECT * FROM osmtype o";
        return (List<OSMType>)getSession().createSQLQuery(queryString).addEntity(OSMType.class).list();
    }

    @Override
    public List<OSMType> getUsedOSMTypes() {
        String queryString = "SELECT * FROM osmtype o JOIN tiptype t ON o.tiptypeid = t.id";
        return (List<OSMType>)getSession().createSQLQuery(queryString).addEntity(OSMType.class).list();
    }
}
