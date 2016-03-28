package com.mmontes.model.dao;

import com.mmontes.model.entity.OSM.OSMType;
import com.mmontes.model.util.genericdao.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("OSMTypeDao")
@SuppressWarnings("all")
public class OSMTypeDaoHibernate extends GenericDaoHibernate<OSMType,Long> implements OSMTypeDao {
    @Override
    public List<OSMType> getOSMTypes(Boolean tipTypeSetted) {
        String queryString = "SELECT * FROM osmtype o ";
        if (tipTypeSetted != null && tipTypeSetted){
            queryString += "JOIN tiptype t ON o.tiptypeid = t.id";
        }
        return (List<OSMType>)getSession().createSQLQuery(queryString).addEntity(OSMType.class).list();
    }
}
