package com.mmontes.model.dao;

import com.mmontes.model.entity.TIP.TIPtype;
import com.mmontes.model.util.genericdao.GenericDao;

import java.util.List;

public interface TIPtypeDao extends GenericDao<TIPtype, Long> {

    List<TIPtype> findAll();
}
