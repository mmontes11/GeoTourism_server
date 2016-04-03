package com.mmontes.model.service;

import com.mmontes.model.dao.*;
import com.mmontes.model.entity.City;
import com.mmontes.model.entity.TIP.TIP;
import com.mmontes.model.entity.UserAccount;
import com.mmontes.model.entity.route.Route;
import com.mmontes.service.GoogleMapsService;
import com.mmontes.util.GeometryUtils;
import com.mmontes.util.URLvalidator;
import com.mmontes.util.dto.*;
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
    create(Long typeId, String name, String description, String photoUrl, String infoUrl, Geometry geom, Long osmId, boolean reviewed)
            throws TIPLocationException, InvalidTIPUrlException, InstanceNotFoundException {

        TIP tip = new TIP();
        tip.setType(tipTypeDao.findById(typeId));
        tip.setName(name);
        tip.setDescription(description);
        tip.setGeom(geom);
        tip.setPhotoUrl(photoUrl);
        tip.setInfoUrl(infoUrl);
        tip.setOsmId(osmId);
        tip.setReviewed(reviewed);

        Coordinate coordinate = tip.getGeom().getCoordinate();
        tip.setGoogleMapsUrl(googleMapsService.getTIPGoogleMapsUrl(coordinate));
        try {
            tip.setAddress(googleMapsService.getAddress(coordinate));
        } catch (Exception e) {
            e.printStackTrace();
            throw new TIPLocationException("Invalid TIP address");
        }

        City city = cityService.getCityFromLocation(geom);
        tip.setCity(city);

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
    public TIPRouteDto findById(Long TIPId) throws InstanceNotFoundException {
        return dtoService.TIP2TIPRouteDto(tipDao.findById(TIPId));
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

    public List<FeatureSearchDto> find(String boundsWKT, List<Long> typeIds, List<Long> cityIds, List<Long> facebookUserIds, List<Long> routes, boolean reviewed)
            throws InstanceNotFoundException {
        List<TIP> tips = tipDao.find(boundsWKT, typeIds, cityIds, facebookUserIds, routes, reviewed);
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

    @Override
    public int getNumRoutes(Long tipId) throws InstanceNotFoundException {
        TIP tip = tipDao.findById(tipId);
        return tip.getRouteTIPs().size();
    }

    @Override
    public void syncTIPs(List<TIPSyncDto> tipSyncDtos)  {
        List<Long> osmIds = new ArrayList<>();
        for (TIPSyncDto tipSyncDto : tipSyncDtos) {
            try {
                Long osmId = tipSyncDto.getOsm_id();
                osmIds.add(osmId);

                Long tipTypeId = tipSyncDto.getTip_type_id();
                tipTypeDao.findById(tipTypeId);

                Geometry geom = GeometryUtils.latLong2Geom(tipSyncDto.getLon(), tipSyncDto.getLat());

                try {
                    TIP tip = tipDao.findByOSMId(osmId);
                    edit(tip.getId(), null, tipTypeId, tipSyncDto.getName(), null, tipSyncDto.getInfo_url(), tip.getAddress(), tipSyncDto.getPhoto_url());
                } catch (InstanceNotFoundException e) {
                    create(tipTypeId, tipSyncDto.getName(), null, tipSyncDto.getPhoto_url(), tipSyncDto.getInfo_url(), geom, osmId, true);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        tipDao.deleteNonExistingFromOSMIds(osmIds);
    }

    @Override
    public void review(Long tipId) throws InstanceNotFoundException {
        TIP tip = tipDao.findById(tipId);
        tip.setReviewed(true);
        tipDao.save(tip);
    }
}
