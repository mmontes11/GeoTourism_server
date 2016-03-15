package com.mmontes.model.service;

import com.mmontes.model.entity.TIP.TIPtype;
import com.mmontes.util.dto.TIPtypeDetailsDto;
import com.mmontes.util.exception.InstanceNotFoundException;

import java.util.List;

public interface TIPtypeService {

    List<TIPtype> findAllTypes();
    TIPtypeDetailsDto findById(Long TIPtypeId) throws InstanceNotFoundException;
    String findTypeName(Long TIPtypeId) throws InstanceNotFoundException;
    TIPtypeDetailsDto create(String name,String icon) throws InstanceNotFoundException;
}
