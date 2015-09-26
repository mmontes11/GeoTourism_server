package com.mmontes.service.internal;

import com.mmontes.model.dao.TIPDao;
import com.mmontes.model.entity.City;
import com.mmontes.model.entity.TIP.*;
import com.mmontes.service.external.AmazonService;
import com.mmontes.service.external.WikipediaService;
import com.mmontes.util.dto.DtoConversor;
import com.mmontes.util.dto.TIPDto;
import com.mmontes.util.exception.AmazonServiceExeption;
import com.mmontes.util.exception.TIPLocationException;
import com.mmontes.util.exception.WikipediaServiceException;
import com.vividsolutions.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mmontes.util.GlobalNames.*;

@Service("TIPService")
@Transactional
public class TIPServiceImpl implements TIPService {

    @Autowired
    private TIPDao tipDao;

    @Autowired
    private CityService cityService;

    public TIPDto create(String type, String name, String description, String photoUrl, String photoContent, String infoUrl, Geometry geom) throws AmazonServiceExeption, TIPLocationException, WikipediaServiceException {

        TIP tip = null;
        if (type.equals(MONUMENT_DISCRIMINATOR)){
            tip = new Monument();
        }
        if (type.equals(NATURAL_SPACE_DISCRIMINATOR)){
            tip = new NaturalSpace();
        }
        if (type.equals(HOTEL_DISCRIMINATOR)){
            tip = new Hotel();
        }
        if (type.equals(RESTAURANT_DISCRIMINATOR)){
            tip = new Restaurant();
        }
        if (tip == null){
            tip = new TIP();
        }
        tip.setName(name);
        tip.setDescription(description);
        tip.setGeom(geom);
        if (photoUrl != null){
            tip.setPhotoUrl(photoUrl);
        }else{
            String url = null;
            try {
                url = AmazonService.uploadFile(name, ".png", photoContent);
                tip.setPhotoUrl(url);
            } catch (Exception e) {
                throw new AmazonServiceExeption();
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
            String regionDomain = city.getRegion().getDomain();
            String countryDomain = city.getRegion().getCountry().getDomain();
            String domain = regionDomain != null? regionDomain : countryDomain;
            String url = null;
            try {
                url = WikipediaService.getWikipediaUrl(domain,name);
                tip.setInfoUrl(url);
            } catch (Exception e) {
                throw new WikipediaServiceException();
            }
        }
        tipDao.save(tip);
        return DtoConversor.TIP2TIPDto(tip);
    }

    public void edit(Long TIPId, String type, String name, String description, String photoUrl, String infoUrl, Geometry geom) {

    }

    public TIPDto findById(Long TIPId) {
        return null;
    }

    public boolean exists(Long TIPId) {
        return false;
    }

    public void remove(Long TIPId) {

    }

    public List<TIPDto> find(Long facebookUserId, Geometry location, String type, Long cityId, Integer favouritedBy) {
        return null;
    }
}
