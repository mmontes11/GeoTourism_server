package com.mmontes.model.service.internal;

import com.mmontes.model.dao.TIPDao;
import com.mmontes.model.entity.City;
import com.mmontes.model.entity.TIP;
import com.mmontes.model.service.external.AmazonService;
import com.mmontes.model.service.external.GoogleMapsService;
import com.mmontes.model.service.external.WikipediaService;
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

import static com.mmontes.util.Constants.*;

@Service("TIPService")
@Transactional
public class TIPServiceImpl implements TIPService {

    @Autowired
    private TIPDao tipDao;

    @Autowired
    private CityService cityService;

    public TIPDetailsDto
    create(String type, String name, String description, String photoUrl, String photoContent, String photoName, String infoUrl, Geometry geom)
            throws AmazonServiceExeption, TIPLocationException, WikipediaServiceException, InvalidTIPUrlException, GoogleMapsServiceException {

        TIP tip = new TIP();
        tip.setType(type);
        tip.setName(name);
        tip.setDescription(description);
        tip.setGeom(geom);

        Coordinate coordinate = tip.getGeom().getCoordinate();
        tip.setGoogleMapsUrl(GoogleMapsService.getTIPGoogleMapsUrl(coordinate));
        try {
            tip.setAddress(GoogleMapsService.getAddress(coordinate));
        } catch (Exception e) {
            e.printStackTrace();
            throw new GoogleMapsServiceException();
        }

        if (photoUrl != null && !photoUrl.equals("")){
            tip.setPhotoUrl(photoUrl);
        }else{
            if (photoName != null && photoContent != null){
                String url;
                try {
                    url = AmazonService.uploadFile(photoName, photoContent);
                    tip.setPhotoUrl(url);
                } catch (Exception e) {
                    throw new AmazonServiceExeption();
                }
            }
        }

        City city = cityService.getCityFromLocation(geom);
        if (city != null){
            tip.setCity(city);
        }else{
            throw new TIPLocationException();
        }

        if (infoUrl != null){
            tip.setInfoUrl(infoUrl);
        }else{
            if (type.equals(MONUMENT_DISCRIMINATOR) || type.equals(NATURAL_SPACE_DISCRIMINATOR)){
                String regionDomain = city.getRegion().getDomain();
                String countryDomain = city.getRegion().getCountry().getDomain();
                String domain = regionDomain != null? regionDomain : countryDomain;
                String url;
                try {
                    url = WikipediaService.getWikipediaUrl(domain,name);
                    tip.setInfoUrl(url);
                } catch (Exception e) {
                    throw new WikipediaServiceException();
                }
            }
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

    public List<TIPSearchDto> find(Long facebookUserId, Geometry location, String type, Long cityId, Integer favouritedBy, Double radius) {
        List<TIP> tips = tipDao.find(location,type,cityId,null,radius);
        return DtoConversor.ListTIP2ListSearchDto(tips);
    }

    public TIPDetailsDto edit(Long TIPId, TIPPatchRequest newData) throws InstanceNotFoundException, AmazonServiceExeption {
        TIP tip = tipDao.findById(TIPId);
        tip.setType(newData.getType());
        tip.setName(newData.getName());
        tip.setDescription(newData.getDescription());
        tip.setInfoUrl(newData.getInfoUrl());
        if (newData.getPhotoUrl() != null && !newData.getPhotoUrl().equals("")){
            tip.setPhotoUrl(newData.getPhotoUrl());
        }else{
            if (newData.getPhotoName() != null && newData.getPhotoContent() != null){
                String url;
                try {
                    url = AmazonService.uploadFile(newData.getPhotoName(), newData.getPhotoContent());
                    tip.setPhotoUrl(url);
                } catch (Exception e) {
                    throw new AmazonServiceExeption();
                }
            }
        }
        tipDao.save(tip);
        return DtoConversor.TIP2TIPDetailsDto(tip);
    }
}
