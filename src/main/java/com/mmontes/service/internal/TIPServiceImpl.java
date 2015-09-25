package com.mmontes.service.internal;

import com.mmontes.model.dao.TIPDao;
import com.mmontes.model.entity.TIP.*;
import com.mmontes.service.external.AmazonService;
import com.mmontes.util.dto.TIPDto;
import com.mmontes.util.exception.AmazonServiceExeption;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
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

    public TIP create(String type, String name, String description, String photoUrl, String photoContent, String infoUrl, Geometry geom) throws AmazonServiceExeption {

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
        if (infoUrl != null){
            tip.setInfoUrl(infoUrl);
        }else{
            //WikipediService
        }
        return null;
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
