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
            throws InstanceNotFoundException, GoogleMapsServiceException, InvalidRouteException;
    RouteDetailsDto edit(Long routeId, String name, String description, String travelMode, List<Long> tipIds, Long facebookUserId)
            throws InstanceNotFoundException, GoogleMapsServiceException, InvalidRouteException;
    RouteDetailsDto findById(Long routeId) throws InstanceNotFoundException;
    List<FeatureSearchDto> find(Geometry bounds, String travelMode, List<Long> cityIds, Integer createdBy, Long facebookUserId, List<Long> friendsFacebookUserIds);
    void remove(Long routeId) throws InstanceNotFoundException;
    List<TIPMinDto> getTIPsInOrder(Long routeID)
            throws InstanceNotFoundException;
}
