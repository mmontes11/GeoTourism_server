package com.mmontes.model.service;

import com.mmontes.model.dao.RouteDao;
import com.mmontes.model.dao.TIPDao;
import com.mmontes.model.dao.UserAccountDao;
import com.mmontes.model.entity.TIP.TIP;
import com.mmontes.model.entity.UserAccount;
import com.mmontes.model.entity.route.Route;
import com.mmontes.model.entity.route.RouteTIP;
import com.mmontes.service.GoogleMapsService;
import com.mmontes.util.GeometryUtils;
import com.mmontes.util.dto.DtoService;
import com.mmontes.util.dto.FeatureSearchDto;
import com.mmontes.util.dto.RouteDetailsDto;
import com.mmontes.util.dto.TIPMinDto;
import com.mmontes.util.exception.GoogleMapsServiceException;
import com.mmontes.util.exception.InstanceNotFoundException;
import com.mmontes.util.exception.InvalidRouteException;
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
    private UserAccountService userAccountService;

    @Autowired
    private DtoService dtoService;

    @Autowired
    private GoogleMapsService googleMapsService;

    @Autowired
    private TIPService tipService;

    private List<Coordinate> setRouteTIPsAndGetCoords(Route route, List<Long> tipIds) throws InstanceNotFoundException {
        List<Coordinate> coordinates = new ArrayList<>();
        for (int i = 0; i < tipIds.size(); i++) {
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

    private void validateRouteParams(String travelMode, List<Geometry> partialGeoms, List<Long> tipIds) throws InvalidRouteException {
        if (travelMode != null && !googleMapsService.isValidTravelMode(travelMode)) {
            throw new InvalidRouteException("Invalid travel mode");
        }
        if (partialGeoms != null && partialGeoms.size() < 2) {
            throw new InvalidRouteException("Invalid number of partial geometries(" + partialGeoms.size() + ")");
        }
        if (tipIds.size() < 2) {
            throw new InvalidRouteException("Invalid number of TIP IDs(" + tipIds.size() + ")");
        }
    }

    private Geometry getCompleteRouteGeom(List<Geometry> partialGeoms, List<Long> tipIds) throws InvalidRouteException {
        Geometry routeGeom = GeometryUtils.unionGeometries(partialGeoms);
        if (!tipService.geometryContainsTIPs(routeGeom, tipIds)) {
            throw new InvalidRouteException("TIPs not contained inside geometry");
        }
        return routeGeom;
    }

    @Override
    public RouteDetailsDto create(String name, String description, String travelMode, List<Geometry> partialGeoms, List<Long> tipIds, Long facebookUserId)
            throws InstanceNotFoundException, InvalidRouteException {
        validateRouteParams(travelMode, partialGeoms, tipIds);
        UserAccount creator = userAccountDao.findByFBUserID(facebookUserId);
        Geometry routeGeom = null;
        if (partialGeoms != null && !partialGeoms.isEmpty()) {
            routeGeom = getCompleteRouteGeom(partialGeoms, tipIds);
        }
        Route route = new Route();
        route.setName(name);
        route.setDescription(description);
        route.setTravelMode(travelMode);
        List<Coordinate> coordinates = setRouteTIPsAndGetCoords(route, tipIds);
        if (routeGeom == null) {
            try {
                routeGeom = googleMapsService.getRoute(coordinates, route.getTravelMode());
            } catch (GoogleMapsServiceException e) {
                throw new InvalidRouteException("Invalid Route");
            }
        }
        route.setGeom(routeGeom);
        route.setGoogleMapsUrl(googleMapsService.getRouteGoogleMapsUrl(coordinates, route.getTravelMode()));
        route.setCreator(creator);
        routeDao.save(route);
        return dtoService.Route2RouteDetailsDto(route, creator);
    }

    @Override
    public RouteDetailsDto edit(Long routeId, Long facebookUserId, String name, String description, String travelMode, List<Long> tipIds)
            throws InstanceNotFoundException, InvalidRouteException {
        validateRouteParams(travelMode, null, tipIds);
        UserAccount creator = userAccountDao.findByFBUserID(facebookUserId);
        Route route = routeDao.getRouteByIDandUser(routeId, facebookUserId);
        route.setName(name);
        route.setDescription(description);
        route.setTravelMode(travelMode);
        route.getRouteTIPs().clear();
        routeDao.getTIPsInOrder(route.getId());
        List<Coordinate> coordinates = setRouteTIPsAndGetCoords(route, tipIds);
        try {
            route.setGeom(googleMapsService.getRoute(coordinates, route.getTravelMode()));
        } catch (GoogleMapsServiceException e) {
            throw new InvalidRouteException("Invalid Route");
        }
        route.setGoogleMapsUrl(googleMapsService.getRouteGoogleMapsUrl(coordinates, route.getTravelMode()));
        routeDao.save(route);
        return dtoService.Route2RouteDetailsDto(route, creator);
    }

    @Override
    public void updateRouteFromTIPs(Route route) throws InvalidRouteException {
        List<TIP> tips = routeDao.getTIPsInOrder(route.getId());
        List<Coordinate> coordinates = new ArrayList<>();
        for (TIP tip : tips) {
            coordinates.add(tip.getGeom().getCoordinate());
        }
        try {
            route.setGeom(googleMapsService.getRoute(coordinates, route.getTravelMode()));
        } catch (GoogleMapsServiceException e) {
            throw new InvalidRouteException("Invalid Route");
        }
        route.setGoogleMapsUrl(googleMapsService.getRouteGoogleMapsUrl(coordinates, route.getTravelMode()));
        routeDao.save(route);
    }

    @Override
    public RouteDetailsDto findById(Long routeId, Long facebookUserId) throws InstanceNotFoundException {
        Route route;
        UserAccount creator = null;
        if (facebookUserId != null) {
            creator = userAccountDao.findByFBUserID(facebookUserId);
            route = routeDao.getRouteByIDandUser(routeId, facebookUserId);
        } else {
            route = routeDao.findById(routeId);
        }
        return dtoService.Route2RouteDetailsDto(route, creator);
    }

    @Override
    public List<FeatureSearchDto> find(Geometry bounds, List<String> travelModes, Integer createdBy, Long facebookUserId, List<Long> friendsFacebookUserIds)
            throws InstanceNotFoundException {
        List<Long> facebookUserIds = userAccountService.getFacebookUserIds(createdBy, facebookUserId, friendsFacebookUserIds);
        List<Route> routes = routeDao.find(bounds, travelModes, facebookUserIds);
        return dtoService.ListRoute2ListFeatureSearchDto(routes);
    }

    @Override
    public void remove(Long routeId, Long facebookUserId) throws InstanceNotFoundException {
        Route route = routeDao.getRouteByIDandUser(routeId, facebookUserId);
        routeDao.remove(route.getId());
    }

    @Override
    public List<TIPMinDto> getTIPsInOrder(Long routeID) throws InstanceNotFoundException {
        Route route = routeDao.findById(routeID);
        List<TIP> tips = routeDao.getTIPsInOrder(route.getId());
        return dtoService.ListTIP2ListTIPMinDto(tips);
    }

    @Override
    public Geometry getShortestPath(Long TIPIdOrigin, Long TIPIdDestination, String travelMode) throws InstanceNotFoundException, InvalidRouteException {
        List<Long> tipIds = new ArrayList<>();
        tipIds.add(TIPIdOrigin);
        tipIds.add(TIPIdDestination);
        validateRouteParams(travelMode,null,tipIds);

        TIP origin = tipDao.findById(TIPIdOrigin);
        TIP destination = tipDao.findById(TIPIdDestination);
        List<Coordinate> coordinates = new ArrayList<>();
        coordinates.add(origin.getGeom().getCoordinate());
        coordinates.add(destination.getGeom().getCoordinate());
        try {
            return googleMapsService.getRoute(coordinates,travelMode);
        } catch (GoogleMapsServiceException e) {
            throw new InvalidRouteException("Invalid partial route");
        }
    }
}
