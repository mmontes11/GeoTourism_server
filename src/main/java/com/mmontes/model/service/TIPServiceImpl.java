package com.mmontes.model.service;

import com.mmontes.model.dao.TIPDao;
import com.mmontes.model.dao.TIPtypeDao;
import com.mmontes.model.entity.City;
import com.mmontes.model.entity.TIP;
import com.mmontes.service.GoogleMapsService;
import com.mmontes.rest.request.TIPPatchRequest;
import com.mmontes.util.URLvalidator;
import com.mmontes.util.dto.DtoConversor;
import com.mmontes.util.dto.TIPDetailsDto;
import com.mmontes.util.dto.TIPSearchDto;
import com.mmontes.util.exception.*;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("TIPService")
@Transactional
public class TIPServiceImpl implements TIPService {

    @Autowired
    private TIPDao tipDao;

    @Autowired
    private TIPtypeDao tipTypeDao;

    @Autowired
    private CityService cityService;

    public TIPDetailsDto
    create(Long typeId, String name, String description, String photoUrl, String infoUrl, Geometry geom)
            throws TIPLocationException, InvalidTIPUrlException, GoogleMapsServiceException, InstanceNotFoundException {

        TIP tip = new TIP();
        tip.setType(tipTypeDao.findById(typeId));
        tip.setName(name);
        tip.setDescription(description);
        tip.setGeom(geom);
        tip.setPhotoUrl(photoUrl);
        tip.setInfoUrl(infoUrl);

        Coordinate coordinate = tip.getGeom().getCoordinate();
        tip.setGoogleMapsUrl(GoogleMapsService.getTIPGoogleMapsUrl(coordinate));
        try {
            tip.setAddress(GoogleMapsService.getAddress(coordinate));
        } catch (Exception e) {
            e.printStackTrace();
            throw new GoogleMapsServiceException();
        }

        City city = cityService.getCityFromLocation(geom);
        if (city != null){
            tip.setCity(city);
        }else{
            throw new TIPLocationException();
        }

        URLvalidator.checkURLs(tip);

        tipDao.save(tip);
        return DtoConversor.TIP2TIPDetailsDto(tip);
    }

    public TIPDetailsDto findById(Long TIPId) throws InstanceNotFoundException {
        return DtoConversor.TIP2TIPDetailsDto(tipDao.findById(TIPId));
    }

    public boolean exists(Long TIPId) {
        return tipDao.exists(TIPId);
    }

    public void remove(Long TIPId) throws InstanceNotFoundException {
        tipDao.remove(TIPId);
    }

    public List<TIPSearchDto> find(Long facebookUserId, Geometry bounds, List<Long> typeIds, List<Long> cityIds, Integer favouritedBy) {
        List<TIP> tips = tipDao.find(bounds,typeIds,cityIds,null);
        return DtoConversor.ListTIP2ListSearchDto(tips);
    }

    public TIPDetailsDto edit(Long TIPId, TIPPatchRequest newData) throws InstanceNotFoundException {
        TIP tip = tipDao.findById(TIPId);
        tip.setType(tipTypeDao.findById(newData.getType()));
        tip.setName(newData.getName());
        tip.setDescription(newData.getDescription());
        tip.setInfoUrl(newData.getInfoUrl());
        tip.setAddress(newData.getAddress());
        if (newData.getPhotoUrl() != null && !newData.getPhotoUrl().equals("")){
            tip.setPhotoUrl(newData.getPhotoUrl());
        }
        tipDao.save(tip);
        return DtoConversor.TIP2TIPDetailsDto(tip);
    }
}
