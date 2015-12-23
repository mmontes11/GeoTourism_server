package com.mmontes.model.service;

import com.mmontes.model.entity.TIP.TIPtype;
import com.mmontes.util.exception.InstanceNotFoundException;

import java.util.List;

public interface TIPtypeService {

    List<TIPtype> findAllTypes();
    
    String findTypeName(Long TIPtypeId) throws InstanceNotFoundException;
}
