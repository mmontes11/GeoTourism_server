package com.mmontes.model.dao;

import com.mmontes.model.entity.TIPtype;
import com.mmontes.model.util.genericdao.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

@Repository("TIPtypeDao")
public class TIPtypeDatoHibernate extends GenericDaoHibernate<TIPtype,Long> implements TIPtypeDao{

}
