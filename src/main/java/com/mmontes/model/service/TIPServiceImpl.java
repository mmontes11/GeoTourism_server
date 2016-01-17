package com.mmontes.model.service;

import com.mmontes.model.dao.RouteDao;
import com.mmontes.model.dao.TIPDao;
import com.mmontes.model.dao.TIPtypeDao;
import com.mmontes.model.dao.UserAccountDao;
import com.mmontes.model.entity.City;
import com.mmontes.model.entity.TIP.TIP;
import com.mmontes.model.entity.UserAccount;
import com.mmontes.model.entity.route.Route;
import com.mmontes.service.GoogleMapsService;
import com.mmontes.util.URLvalidator;
import com.mmontes.util.dto.DtoService;
import com.mmontes.util.dto.FeatureSearchDto;
import com.mmontes.util.dto.TIPDetailsDto;
import com.mmontes.util.dto.TIPMinDto;
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
    private RouteDao routeDao;

    @Autowired
    private RouteService routeService;

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
            throws TIPLocationException, InvalidTIPUrlException, InstanceNotFoundException {

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
            throw new TIPLocationException("Invalid TIP address");
        }

        City city = cityService.getCityFromLocation(geom);
        if (city != null) {
            tip.setCity(city);
        } else {
            throw new TIPLocationException("TIP location is incorrect. It should be located in a single City");
        }

        URLvalidator.checkURLs(tip);

        tipDao.save(tip);
        return dtoService.TIP2TIPDetailsDto(tip, null);
    }

    public TIPDetailsDto findById(Long TIPId, Long facebooUserId) throws InstanceNotFoundException {
        UserAccount userAccount = null;
        if (facebooUserId != null) {
            userAccount = userAccountDao.findByFBUserID(facebooUserId);
        }
        return dtoService.TIP2TIPDetailsDto(tipDao.findById(TIPId), userAccount);
    }

    @Override
    public TIPMinDto findById(Long TIPId) throws InstanceNotFoundException {
        return dtoService.TIP2TIPMinDto(tipDao.findById(TIPId));
    }

    public void remove(Long TIPId) throws InstanceNotFoundException, InvalidRouteException {
        List<Route> routes = routeDao.getRoutesByTIP(TIPId);
        tipDao.remove(TIPId);
        for (Route route : routes) {
            if (routeDao.getTIPsInOrder(route.getId()).size() < 2) {
                routeDao.remove(route.getId());
            } else {
                routeService.updateRouteFromTIPs(route);
            }
        }
    }

    public List<FeatureSearchDto> find(String boundsWKT, List<Long> typeIds, List<Long> cityIds, List<Long> facebookUserIds) throws InstanceNotFoundException {
        List<TIP> tips = tipDao.find(boundsWKT, typeIds, cityIds, facebookUserIds);
        return dtoService.ListTIP2ListFeatureSearchDto(tips);
    }

    public TIPDetailsDto edit(Long TIPId, Long facebooUserId, Long type, String name, String description, String infoUrl, String address, String photoUrl)
            throws InstanceNotFoundException, InvalidTIPUrlException {
        TIP tip = tipDao.findById(TIPId);
        UserAccount userAccount = null;
        if (facebooUserId != null) {
            userAccount = userAccountDao.findByFBUserID(facebooUserId);
        }
        tip.setType(tipTypeDao.findById(type));
        tip.setName(name);
        tip.setDescription(description);
        tip.setInfoUrl(infoUrl);
        tip.setAddress(address);
        tip.setPhotoUrl(photoUrl);

        URLvalidator.checkURLs(tip);

        tipDao.save(tip);
        return dtoService.TIP2TIPDetailsDto(tip, userAccount);
    }

    @Override
    public boolean geometryContainsTIPs(Geometry superGeometry, List<Long> tipIds) {
        return tipDao.geometryContainsTIPs(superGeometry, tipIds);
    }
}
