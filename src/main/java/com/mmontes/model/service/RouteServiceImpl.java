package com.mmontes.model.service;

import com.mmontes.model.dao.RouteDao;
import com.mmontes.model.dao.TIPDao;
import com.mmontes.model.dao.UserAccountDao;
import com.mmontes.model.entity.TIP.TIP;
import com.mmontes.model.entity.route.Route;
import com.mmontes.model.entity.route.RouteTIP;
import com.mmontes.service.GoogleMapsService;
import com.mmontes.util.dto.DtoService;
import com.mmontes.util.dto.FeatureSearchDto;
import com.mmontes.util.dto.RouteDetailsDto;
import com.mmontes.util.dto.TIPMinDto;
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

    @Override
    public RouteDetailsDto createRoute(String name, String description, List<String> lineStrings, List<Long> tipIds, Long facebookUserId) throws InstanceNotFoundException, GoogleMapsServiceException {
        Route route = new Route();
        route.setName(name);
        route.setDescription(description);
        List<TIP> tips = new ArrayList<>();
        List<Coordinate> coordinates = new ArrayList<>();
        for (Long tipId : tipIds){
            TIP tip = tipDao.findById(tipId);
            tips.add(tip);
            coordinates.add(tip.getGeom().getCoordinate());
        }
        if (lineStrings != null || !lineStrings.isEmpty()){

        }else{
            Geometry geom = googleMapsService.getRoute(coordinates);
            route.setGeom(geom);
        }
        for (int i = 0; i<tips.size(); i++){
            RouteTIP routeTIP = new RouteTIP();
            routeTIP.setRoute(route);
            routeTIP.setTip(tips.get(i));
            routeTIP.setOrdination(i);
            route.getRouteTIPs().add(routeTIP);
        }
        route.setCreator(userAccountDao.findByFBUserID(facebookUserId));
        routeDao.save(route);
        return dtoService.Route2RouteDetailsDto(route);
    }

    @Override
    public RouteDetailsDto editRoute(Long routeId, String name, String description, List<Long> tipIds) {
        return null;
    }

    @Override
    public List<FeatureSearchDto> find(Geometry bounds, List<Long> cityIds, Integer createdBy, Long facebookUserId, List<Long> friendsFacebookUserIds) throws InstanceNotFoundException {
        return null;
    }

    @Override
    public List<TIPMinDto> getTIPsInOrder(Long routeID) throws InstanceNotFoundException {
        Route route = routeDao.findById(routeID);
        List<TIP> tips = routeDao.getTIPsInOrder(route.getId());
        return dtoService.ListTIP2ListTIPMinDto(tips);
    }
}