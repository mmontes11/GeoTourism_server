package com.mmontes.model.service;

import com.mmontes.model.dao.TIPDao;
import com.mmontes.model.dao.TIPtypeDao;
import com.mmontes.model.dao.UserAccountDao;
import com.mmontes.model.entity.City;
import com.mmontes.model.entity.TIP;
import com.mmontes.model.entity.UserAccount;
import com.mmontes.service.GoogleMapsService;
import com.mmontes.rest.request.TIPPatchRequest;
import com.mmontes.util.URLvalidator;
import com.mmontes.util.dto.DtoService;
import com.mmontes.util.dto.TIPDetailsDto;
import com.mmontes.util.dto.TIPSearchDto;
import com.mmontes.util.exception.*;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Autowired
    private UserAccountDao userAccountDao;

    @Autowired
    private DtoService dtoService;

    @Autowired
    private GoogleMapsService googleMapsService;

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
        tip.setGoogleMapsUrl(googleMapsService.getTIPGoogleMapsUrl(coordinate));
        try {
            tip.setAddress(googleMapsService.getAddress(coordinate));
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
        return dtoService.TIP2TIPDetailsDto(tip,null);
    }

    public TIPDetailsDto findById(Long TIPId,Long facebooUserId) throws InstanceNotFoundException {
        UserAccount userAccount = null;
        if (facebooUserId != null){
            userAccount = userAccountDao.findByFBUserID(facebooUserId);
        }
        return dtoService.TIP2TIPDetailsDto(tipDao.findById(TIPId), userAccount);
    }

    public boolean exists(Long TIPId) {
        return tipDao.exists(TIPId);
    }

    public void remove(Long TIPId) throws InstanceNotFoundException {
        tipDao.remove(TIPId);
    }

    public List<TIPSearchDto> find( Geometry bounds, List<Long> typeIds, List<Long> cityIds, Integer favouritedBy, Long facebookUserId, List<Long> friendsFacebookUserIds) throws InstanceNotFoundException {
        List<Long> facebookUserIds = new ArrayList<>();
        if (favouritedBy != null){
            if (favouritedBy == 0){
                facebookUserIds.add(facebookUserId);
            } else if (favouritedBy == 1){
                if (friendsFacebookUserIds != null && !friendsFacebookUserIds.isEmpty()){
                    facebookUserIds.addAll(friendsFacebookUserIds);
                }else{
                    UserAccount userAccount = userAccountDao.findByFBUserID(facebookUserId);
                    for (UserAccount user : userAccount.getFriends()){
                        facebookUserIds.add(user.getFacebookUserId());
                    }
                }
            }
        }
        List<TIP> tips = tipDao.find(bounds,typeIds,cityIds,facebookUserIds);
        return dtoService.ListTIP2ListSearchDto(tips);
    }

    public TIPDetailsDto edit(Long TIPId, Long facebooUserId, TIPPatchRequest newData) throws InstanceNotFoundException {
        TIP tip = tipDao.findById(TIPId);
        UserAccount userAccount = null;
        if (facebooUserId != null){
            userAccount = userAccountDao.findByFBUserID(facebooUserId);
        }
        tip.setType(tipTypeDao.findById(newData.getType()));
        tip.setName(newData.getName());
        tip.setDescription(newData.getDescription());
        tip.setInfoUrl(newData.getInfoUrl());
        tip.setAddress(newData.getAddress());
        tip.setPhotoUrl(newData.getPhotoUrl());
        tipDao.save(tip);
        return dtoService.TIP2TIPDetailsDto(tip,userAccount);
    }
}
