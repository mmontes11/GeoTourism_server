package com.mmontes.model.dao;

import com.mmontes.model.entity.OSMType;
import com.mmontes.model.util.genericdao.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("OSMTypeDao")
public class OSMTypeDaoHibernate extends GenericDaoHibernate<OSMType,Long> implements OSMTypeDao {
    @Override
    @SuppressWarnings("all")
    public List<OSMType> getOSMTypes() {
        String queryString = "SELECT o FROM OSMType o";
        return (List<OSMType>)getSession().createQuery(queryString).list();
    }
}
