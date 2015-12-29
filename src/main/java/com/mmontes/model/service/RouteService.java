package com.mmontes.model.service;

import com.mmontes.util.dto.FeatureSearchDto;
import com.mmontes.util.dto.RouteDetailsDto;
import com.mmontes.util.dto.TIPMinDto;
import com.mmontes.util.exception.GoogleMapsServiceException;
import com.mmontes.util.exception.InstanceNotFoundException;
import com.vividsolutions.jts.geom.Geometry;

import java.util.List;

public interface RouteService {
    RouteDetailsDto createRoute(String name,String description,String travelMode,List<String> lineStrings,List<Long> tipIds,Long facebookUserId) throws InstanceNotFoundException, GoogleMapsServiceException;
    RouteDetailsDto editRoute(Long routeId,String name,String description,String travelMode,List<Long> tipIds);
    List<FeatureSearchDto> find(Geometry bounds, String travelMode, List<Long> cityIds, Integer createdBy, Long facebookUserId, List<Long> friendsFacebookUserIds) throws InstanceNotFoundException;
    List<TIPMinDto> getTIPsInOrder(Long routeID) throws InstanceNotFoundException;
}
