package com.mmontes.model.dao;

import com.mmontes.model.entity.TIPtype;
import com.mmontes.model.util.genericdao.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("TIPtypeDao")
public class TIPtypeDatoHibernate extends GenericDaoHibernate<TIPtype,Long> implements TIPtypeDao{

    @Override
    @SuppressWarnings("unchecked")
    public List<TIPtype> findAll() {
        String queryString = "SELECT tt FROM TIPtype tt";
        return (List<TIPtype>) getSession().createQuery(queryString).list();
    }
}
