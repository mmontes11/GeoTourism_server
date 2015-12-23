package com.mmontes.model.service;


import com.mmontes.model.dao.TIPtypeDao;
import com.mmontes.model.entity.TIP.TIPtype;
import com.mmontes.util.exception.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("TIPtypeService")
@Transactional
public class TIPtypeServiceImpl implements TIPtypeService {

    @Autowired
    private TIPtypeDao tipTypeDao;

    @Override
    public List<TIPtype> findAllTypes() {
        return tipTypeDao.findAll();
    }

    @Override
    public String findTypeName(Long TIPtypeId) throws InstanceNotFoundException {
        return tipTypeDao.findById(TIPtypeId).getName();
    }
}
