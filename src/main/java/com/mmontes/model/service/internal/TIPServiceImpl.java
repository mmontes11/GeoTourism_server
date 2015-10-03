package com.mmontes.model.service.internal;

import com.mmontes.model.dao.TIPDao;
import com.mmontes.model.entity.City;
import com.mmontes.model.entity.TIP;
import com.mmontes.model.service.external.AmazonService;
import com.mmontes.model.service.external.GoogleMapsService;
import com.mmontes.model.service.external.WikipediaService;
import com.mmontes.util.URLvalidator;
import com.mmontes.util.dto.DtoConversor;
import com.mmontes.util.dto.TIPDto;
import com.mmontes.util.exception.AmazonServiceExeption;
import com.mmontes.util.exception.InvalidTIPUrlException;
import com.mmontes.util.exception.TIPLocationException;
import com.mmontes.util.exception.WikipediaServiceException;
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

    public TIPDto create(String type, String name, String description, String photoUrl, String photoContent, String extension, String infoUrl, Geometry geom) throws AmazonServiceExeption, TIPLocationException, WikipediaServiceException, InvalidTIPUrlException {

        TIP tip = new TIP();
        tip.setType(type);
        tip.setName(name);
        tip.setDescription(description);
        tip.setGeom(geom);
        tip.setGoogleMapsUrl(GoogleMapsService.getTIPGoogleMapsUrl(tip.getGeom().getCoordinate()));

        if (photoUrl != null){
            tip.setPhotoUrl(photoUrl);
        }else{
            if (photoContent != null && extension != null){
                String url;
                try {
                    url = AmazonService.uploadFile(name, extension, photoContent);
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

    public List<TIPDto> find(Long facebookUserId, Geometry location, String type, Long cityId, Integer favouritedBy, Double radius) {
        List<TIP> tips = tipDao.find(location,type,cityId,null,radius);
        return DtoConversor.ListTIP2ListTIPDto(tips);
    }
}
