package com.mmontes.model.service;


import com.mmontes.model.dao.OSMTypeDao;
import com.mmontes.model.dao.TIPtypeDao;
import com.mmontes.model.entity.OSM.OSMType;
import com.mmontes.model.entity.TIP.TIPtype;
import com.mmontes.util.dto.DtoService;
import com.mmontes.util.dto.TIPtypeDto;
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
    public TIPtypeDto findById(Long TIPtypeId) throws InstanceNotFoundException {
        TIPtype tipType = tipTypeDao.findById(TIPtypeId);
        return dtoService.TIPtype2TIPtypeDto(tipType);
    }

    @Override
    public String findTypeName(Long TIPtypeId) throws InstanceNotFoundException {
        return tipTypeDao.findById(TIPtypeId).getName();
    }

    @Override
    public TIPtypeDto create(String name, String icon, String OSMKey, String OSMValue) throws InstanceNotFoundException {
        OSMType osmType = null;
        if (OSMKey != null && !OSMKey.isEmpty() && OSMValue != null && !OSMValue.isEmpty()){
            osmType = osmTypeDao.findByKeyValue(OSMKey,OSMValue);
        }
        TIPtype tipType = new TIPtype();
        tipType.setName(name);
        tipType.setIcon(icon);
        tipTypeDao.save(tipType);
        if (osmType != null){
            osmType.setTipType(tipType);
            osmTypeDao.save(osmType);
        }
        return dtoService.TIPtype2TIPtypeDto(tipType);
    }

    @Override
    public TIPtypeDto update(Long TIPtypeID, String name, String icon, String OSMKey, String OSMValue) throws InstanceNotFoundException {
        TIPtype tiType = tipTypeDao.findById(TIPtypeID);
        OSMType osmType = null;
        if (OSMKey != null && !OSMKey.isEmpty() && OSMValue != null && !OSMValue.isEmpty()){
            osmType = osmTypeDao.findByKeyValue(OSMKey,OSMValue);
        }
        tiType.setName(name);
        tiType.setIcon(icon);
        tipTypeDao.save(tiType);
        if (osmType != null){
            osmType.setTipType(tiType);
            osmTypeDao.save(osmType);
        }
        return dtoService.TIPtype2TIPtypeDto(tiType);

    }

    @Override
    public void delete(Long TIPtypeID) throws InstanceNotFoundException {
        TIPtype tipType = tipTypeDao.findById(TIPtypeID);
        OSMType osmType = tipType.getOsmType();
        if (osmType != null){
            osmType.setTipType(null);
            osmTypeDao.save(osmType);
        }
        tipTypeDao.remove(tipType.getId());
    }
}
