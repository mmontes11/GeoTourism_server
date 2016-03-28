package com.mmontes.model.service;


import com.mmontes.model.dao.OSMTypeDao;
import com.mmontes.model.dao.TIPtypeDao;
import com.mmontes.model.entity.TIP.TIPtype;
import com.mmontes.util.dto.DtoService;
import com.mmontes.util.dto.TIPtypeDetailsDto;
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

    @Autowired
    private OSMTypeDao osmTypeDao;

    @Autowired
    private DtoService dtoService;

    @Override
    public List<TIPtype> findAllTypes() {
        return tipTypeDao.findAll();
    }

    @Override
    public TIPtypeDetailsDto findById(Long TIPtypeId) throws InstanceNotFoundException {
        TIPtype tipType = tipTypeDao.findById(TIPtypeId);
        return dtoService.TIPtype2TIPtypeDetailsDto(tipType);
    }

    @Override
    public String findTypeName(Long TIPtypeId) throws InstanceNotFoundException {
        return tipTypeDao.findById(TIPtypeId).getName();
    }

    @Override
    public TIPtypeDetailsDto create(String name, String icon) throws InstanceNotFoundException {
        TIPtype tipType = new TIPtype();
        tipType.setName(name);
        tipType.setIcon(icon);
        tipTypeDao.save(tipType);
        return dtoService.TIPtype2TIPtypeDetailsDto(tipType);
    }
}
