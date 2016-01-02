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
        if (!googleMapsService.isValidTravelMode(travelMode)) {
            throw new InvalidRouteException("Invalid travel mode");
        }
        if (partialGeoms != null && partialGeoms.size() < 2) {
            throw new InvalidRouteException("Invalid number of partial geometries("+partialGeoms.size()+")");
        }
        if (tipIds.size() < 2) {
            throw new InvalidRouteException("Invalid number of TIP IDs ("+tipIds.size()+")");
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
            throws InstanceNotFoundException, GoogleMapsServiceException, InvalidRouteException {
        validateRouteParams(travelMode, partialGeoms, tipIds);
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
            routeGeom = googleMapsService.getRoute(coordinates, route.getTravelMode());
        }
        route.setGeom(routeGeom);
        route.setGoogleMapsUrl(googleMapsService.getRouteGoogleMapsUrl(coordinates, route.getTravelMode()));
        route.setCreator(userAccountDao.findByFBUserID(facebookUserId));
        routeDao.save(route);
        return dtoService.Route2RouteDetailsDto(route);
    }

    @Override
    public RouteDetailsDto edit(Long routeId, String name, String description, String travelMode, List<Long> tipIds, Long facebookUserId)
            throws InstanceNotFoundException, GoogleMapsServiceException, InvalidRouteException {
        validateRouteParams(travelMode, null, tipIds);
        Route route = routeDao.getRouteByIDandUser(routeId, facebookUserId);
        editDetails(route, name, description, travelMode);
        return editTIPs(route, tipIds);
    }

    private RouteDetailsDto editDetails(Route route, String name, String description, String travelMode)
            throws InstanceNotFoundException, GoogleMapsServiceException, InvalidRouteException {
        route.setName(name);
        route.setDescription(description);
        route.setTravelMode(travelMode);
        route.getRouteTIPs().clear();
        routeDao.save(route);
        return dtoService.Route2RouteDetailsDto(route);
    }

    private RouteDetailsDto editTIPs(Route route, List<Long> tipIds)
            throws InstanceNotFoundException, GoogleMapsServiceException, InvalidRouteException {
        List<Coordinate> coordinates = setRouteTIPsAndGetCoords(route, tipIds);
        route.setGeom(googleMapsService.getRoute(coordinates, route.getTravelMode()));
        route.setGoogleMapsUrl(googleMapsService.getRouteGoogleMapsUrl(coordinates, route.getTravelMode()));
        routeDao.save(route);
        return dtoService.Route2RouteDetailsDto(route);
    }

    @Override
    public List<FeatureSearchDto> find(Geometry bounds, String travelMode, List<Long> cityIds, Integer createdBy, Long facebookUserId, List<Long> friendsFacebookUserIds) {
        return null;
    }

    @Override
    public void remove(Long routeId) throws InstanceNotFoundException {
        routeDao.remove(routeId);
    }


    @Override
    public List<TIPMinDto> getTIPsInOrder(Long routeID) throws InstanceNotFoundException {
        Route route = routeDao.findById(routeID);
        List<TIP> tips = routeDao.getTIPsInOrder(route.getId());
        return dtoService.ListTIP2ListTIPMinDto(tips);
    }
}
