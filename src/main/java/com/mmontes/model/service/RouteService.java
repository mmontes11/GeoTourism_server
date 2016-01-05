package com.mmontes.model.service;

import com.mmontes.model.entity.route.Route;
import com.mmontes.util.dto.FeatureSearchDto;
import com.mmontes.util.dto.RouteDetailsDto;
import com.mmontes.util.dto.TIPMinDto;
import com.mmontes.util.exception.GeometryParsingException;
import com.mmontes.util.exception.GoogleMapsServiceException;
import com.mmontes.util.exception.InstanceNotFoundException;
import com.mmontes.util.exception.InvalidRouteException;
import com.vividsolutions.jts.geom.Geometry;

import java.util.List;

public interface RouteService {
    RouteDetailsDto create(String name, String description, String travelMode, List<Geometry> partialGeoms, List<Long> tipIds, Long facebookUserId)
            throws InstanceNotFoundException, InvalidRouteException;
    RouteDetailsDto edit(Long routeId, String name, String description, String travelMode, List<Long> tipIds, Long facebookUserId)
            throws InstanceNotFoundException, InvalidRouteException;
    void updateRouteFromTIPs(Route route)
            throws InstanceNotFoundException, InvalidRouteException;
    RouteDetailsDto findById(Long routeId,Long facebooUserId)
            throws InstanceNotFoundException;
    List<FeatureSearchDto> find(Geometry bounds, List<String> travelModes, Integer createdBy, Long facebookUserId, List<Long> friendsFacebookUserIds) throws InstanceNotFoundException;
    void remove(Long routeId,Long facebookUserId)
            throws InstanceNotFoundException;
    List<TIPMinDto> getTIPsInOrder(Long routeID)
            throws InstanceNotFoundException;
}
