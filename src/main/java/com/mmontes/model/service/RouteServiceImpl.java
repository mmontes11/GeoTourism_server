package com.mmontes.model.service;

import com.mmontes.model.dao.RouteDao;
import com.mmontes.model.dao.TIPDao;
import com.mmontes.model.dao.UserAccountDao;
import com.mmontes.model.entity.TIP.TIP;
import com.mmontes.model.entity.route.Route;
import com.mmontes.model.entity.route.RouteTIP;
import com.mmontes.service.GoogleMapsService;
import com.mmontes.util.GeometryUtils;
import com.mmontes.util.dto.DtoService;
import com.mmontes.util.dto.FeatureSearchDto;
import com.mmontes.util.dto.RouteDetailsDto;
import com.mmontes.util.dto.TIPMinDto;
import com.mmontes.util.exception.GeometryParsingException;
import com.mmontes.util.exception.GoogleMapsServiceException;
import com.mmontes.util.exception.InstanceNotFoundException;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("RouteService")
@Transactional
public class RouteServiceImpl implements RouteService {

    @Autowired
    private RouteDao routeDao;

    @Autowired
    private TIPDao tipDao;

    @Autowired
    private UserAccountDao userAccountDao;

    @Autowired
    private DtoService dtoService;

    @Autowired
    private GoogleMapsService googleMapsService;

    private List<Coordinate> setRouteTIPsAndGetCoords(Route route,List<Long> tipIds) throws InstanceNotFoundException {
        List<Coordinate> coordinates = new ArrayList<>();
        for (int i = 0; i<tipIds.size(); i++){
            Long tipId = tipIds.get(i);
            TIP tip = tipDao.findById(tipId);
            coordinates.add(tip.getGeom().getCoordinate());
            RouteTIP routeTIP = new RouteTIP();
            routeTIP.setRoute(route);
            routeTIP.setTip(tip);
            routeTIP.setOrdination(i);
            route.getRouteTIPs().add(routeTIP);
        }
        return coordinates;
    }

    @Override
    public RouteDetailsDto createRoute(String name, String description, String travelMode, Geometry routeGeom, List<Long> tipIds, Long facebookUserId)
            throws InstanceNotFoundException, GoogleMapsServiceException, GeometryParsingException {
        Route route = new Route();
        route.setName(name);
        route.setDescription(description);
        route.setTravelMode(travelMode);
        List<Coordinate> coordinates = setRouteTIPsAndGetCoords(route,tipIds);
        Geometry geom;
        if (routeGeom != null){
            geom = routeGeom;
        }else{
            geom = googleMapsService.getRoute(coordinates,route.getTravelMode());
        }
        route.setGeom(geom);
        route.setGoogleMapsUrl(googleMapsService.getRouteGoogleMapsUrl(coordinates,route.getTravelMode()));
        route.setCreator(userAccountDao.findByFBUserID(facebookUserId));
        routeDao.save(route);
        return dtoService.Route2RouteDetailsDto(route);
    }

    @Override
    public RouteDetailsDto editRoute(Long routeId, String name, String description, String travelMode, List<Long> tipIds, Long facebookUserId) throws InstanceNotFoundException, GoogleMapsServiceException {
        Route route = routeDao.getRouteByIDandUser(routeId,facebookUserId);
        route.setName(name);
        route.setDescription(description);
        route.setTravelMode(travelMode);
        route.getRouteTIPs().clear();
        List<Coordinate> coordinates = setRouteTIPsAndGetCoords(route,tipIds);
        route.setGeom(googleMapsService.getRoute(coordinates,route.getTravelMode()));
        route.setGoogleMapsUrl(googleMapsService.getRouteGoogleMapsUrl(coordinates,route.getTravelMode()));
        return dtoService.Route2RouteDetailsDto(route);
    }

    @Override
    public List<FeatureSearchDto> find(Geometry bounds, String travelMode, List<Long> cityIds, Integer createdBy, Long facebookUserId, List<Long> friendsFacebookUserIds) throws InstanceNotFoundException {
        return null;
    }


    @Override
    public List<TIPMinDto> getTIPsInOrder(Long routeID) throws InstanceNotFoundException {
        Route route = routeDao.findById(routeID);
        List<TIP> tips = routeDao.getTIPsInOrder(route.getId());
        return dtoService.ListTIP2ListTIPMinDto(tips);
    }
}
