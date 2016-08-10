package com.mmontes.model.service;

import com.mmontes.model.dao.RouteDao;
import com.mmontes.model.dao.TIPDao;
import com.mmontes.model.dao.TIPtypeDao;
import com.mmontes.model.dao.UserAccountDao;
import com.mmontes.model.entity.City;
import com.mmontes.model.entity.TIP.TIP;
import com.mmontes.model.entity.TIP.TIPtype;
import com.mmontes.model.entity.UserAccount;
import com.mmontes.model.entity.route.Route;
import com.mmontes.service.GoogleMapsService;
import com.mmontes.util.GeometryUtils;
import com.mmontes.util.PrivateConstants;
import com.mmontes.util.URLvalidator;
import com.mmontes.util.dto.*;
import com.mmontes.util.exception.*;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
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

    private GoogleMapsService gMapsServiceForUsers = new GoogleMapsService(PrivateConstants.GOOGLE_MAPS_KEY);
    private GoogleMapsService gMapsServiceForETL = new GoogleMapsService(PrivateConstants.GOOGLE_MAPS_KEY_ETL);

    private String getAddress(GoogleMapsService gMapsService, Coordinate coordinate){
        try {
            return gMapsService.getAddress(coordinate);
        } catch (GoogleMapsServiceException e) {
            return null;
        } catch (GoogleMapsAddressException e) {
            return "";
        }
    }

    private TIPDetailsDto
    create(TIPtype tipType, String name, String description, String photoUrl, String infoUrl, Geometry geom, Long osmId,
           boolean reviewed, String address, Long facebookUserId)
           throws TIPLocationException, InvalidTIPUrlException, InstanceNotFoundException {
        TIP tip = new TIP();
        tip.setType(tipType);
        tip.setName(name);
        tip.setDescription(description);
        tip.setGeom(geom);
        tip.setPhotoUrl(photoUrl);
        tip.setInfoUrl(infoUrl);
        tip.setOsmId(osmId);
        tip.setReviewed(reviewed);
        tip.setCreationDate(Calendar.getInstance());
        tip.setUserAccount(userAccountDao.findByFBUserID(facebookUserId));

        Coordinate coordinate = tip.getGeom().getCoordinate();
        tip.setGoogleMapsUrl(gMapsServiceForUsers.getTIPGoogleMapsUrl(coordinate));

        if (address == null){
            address = getAddress(gMapsServiceForUsers,coordinate);
        }
        tip.setAddress(address);

        City city = cityService.getCityFromLocation(geom);
        tip.setCity(city);

        URLvalidator.checkURLs(tip);

        tipDao.save(tip);
        return dtoService.TIP2TIPDetailsDto(tip, null);
    }

    public TIPDetailsDto
    create(Long typeId, String name, String description, String photoUrl, String infoUrl, Geometry geom, Long osmId,
           boolean reviewed, Long facebookUserId)
           throws TIPLocationException, InvalidTIPUrlException, InstanceNotFoundException {
        TIPtype tipType = tipTypeDao.findById(typeId);
        return create(tipType, name, description, photoUrl, infoUrl, geom, osmId, reviewed, null, facebookUserId);
    }

    public TIPDetailsDto
    createSyncTIPs(TIPtype tipType, String name, String description, String photoUrl, String infoUrl, Geometry geom, Long osmId, boolean reviewed)
            throws TIPLocationException, InvalidTIPUrlException, InstanceNotFoundException {
        String address = getAddress(gMapsServiceForETL,geom.getCoordinate());
        return create(tipType, name, description, photoUrl, infoUrl, geom, osmId, reviewed, address, null);
    }

    public TIPDetailsDto edit(Long TIPId, Long facebookUserId, Long type, String name, String description, String infoUrl, String address, String photoUrl)
            throws InstanceNotFoundException, InvalidTIPUrlException {
        TIP tip = tipDao.findById(TIPId);
        UserAccount userAccount = null;
        if (facebookUserId != null) {
            userAccount = userAccountDao.findByFBUserID(facebookUserId);
        }
        TIPtype tipType = tipTypeDao.findById(type);
        return edit(tip, userAccount, tipType, name, description, infoUrl, address, photoUrl);
    }

    public List<FeatureSearchDto> find(String boundsWKT, List<Long> typeIds, List<Long> cityIds, List<Long> facebookUserIds, List<Long> routes, boolean reviewed, String query)
            throws InstanceNotFoundException {
        List<TIP> tips = tipDao.find(boundsWKT, typeIds, cityIds, facebookUserIds, routes, reviewed, query);
        return dtoService.ListTIP2ListFeatureSearchDto(tips);
    }

    public TIPDetailsDto findById(Long TIPId, Long facebookUserId) throws InstanceNotFoundException {
        UserAccount userAccount = null;
        if (facebookUserId != null) {
            userAccount = userAccountDao.findByFBUserID(facebookUserId);
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

    private TIPDetailsDto edit(TIP tip, UserAccount userAccount, TIPtype type, String name, String description,
                               String infoUrl, String address, String photoUrl)
            throws InstanceNotFoundException, InvalidTIPUrlException {
        tip.setType(type);
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
    public void syncTIPs(List<TIPSyncDto> tipSyncDtos) {
        List<Long> osmIds = new ArrayList<>();
        for (TIPSyncDto tipSyncDto : tipSyncDtos) {
            try {
                Long osmId = tipSyncDto.getOsm_id();
                osmIds.add(osmId);

                Long tipTypeId = tipSyncDto.getTip_type_id();
                TIPtype tipType = tipTypeDao.findById(tipTypeId);

                Geometry geom = GeometryUtils.latLong2Geom(tipSyncDto.getLon(), tipSyncDto.getLat());

                try {
                    TIP tip = tipDao.findByOSMId(osmId);
                    edit(tip, null, tipType, tipSyncDto.getName(), null, tipSyncDto.getInfo_url(), tip.getAddress(), tipSyncDto.getPhoto_url());
                } catch (InstanceNotFoundException e) {
                    createSyncTIPs(tipType, tipSyncDto.getName(), null, tipSyncDto.getPhoto_url(), tipSyncDto.getInfo_url(), geom, osmId, true);
                }
            } catch (Exception e) {
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

    @Override
    public void populateAddresses() {
        List<TIP> tips = tipDao.findWithoutAddress();
        System.out.println("Tips sin dirección: " + tips.size());
        for (TIP tip : tips) {
            try {
                tip.setAddress(gMapsServiceForETL.getAddress(tip.getGeom().getCoordinate()));
            } catch (Exception e) {
                tip.setAddress(null);
                break;
            }
            tipDao.save(tip);
        }
        System.out.println("Tips sin dirección una vez finalizado el proceso: " + tipDao.findWithoutAddress().size());
    }
}
