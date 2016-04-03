package com.mmontes.rest.controller;

import com.mmontes.model.service.TIPService;
import com.mmontes.model.service.UserAccountService;
import com.mmontes.util.GeometryUtils;
import com.mmontes.util.dto.FeatureSearchDto;
import com.mmontes.util.dto.TIPRouteDto;
import com.mmontes.util.dto.TIPSyncDto;
import com.mmontes.util.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TIPsController {

    @Autowired
    private TIPService tipService;

    @Autowired
    private UserAccountService userAccountService;

    @RequestMapping(value = "/tips", method = RequestMethod.GET)
    public ResponseEntity<List<FeatureSearchDto>>
    find(@RequestParam(value = "bounds", required = false) String boundsWKT,
         @RequestParam(value = "types", required = false) List<Long> typeIds,
         @RequestParam(value = "cities", required = false) List<Long> cityIds,
         @RequestParam(value = "favouritedBy", required = false) Integer favouritedBy,
         @RequestHeader(value = "FacebookUserId", required = false) Long facebookUserId,
         @RequestParam(value = "friends", required = false) List<Long> friendsFacebookUserIds,
         @RequestParam(value = "routes", required = false) List<Long> routes,
         @RequestParam(value = "reviewed", required = false) Boolean reviewed) {

        try {
            if (boundsWKT != null) {
                GeometryUtils.geometryFromWKT(boundsWKT);
            }
        } catch (GeometryParsingException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (favouritedBy != null && favouritedBy != 0 && favouritedBy != 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        reviewed = reviewed != null? reviewed : true;
        List<FeatureSearchDto> tips;
        try {
            List<Long> facebookUserIds = userAccountService.getFacebookUserIds(favouritedBy, facebookUserId, friendsFacebookUserIds);
            tips = tipService.find(boundsWKT, typeIds, cityIds, facebookUserIds, routes, reviewed);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tips, HttpStatus.OK);
    }

    @RequestMapping(value = "/tips/min", method = RequestMethod.GET)
    public ResponseEntity<List<TIPRouteDto>>
    getTIPMinDTOs(@RequestParam(value = "TIPIds", required = true) List<Long> tipIds) {
        List<TIPRouteDto> tipRouteDtos = new ArrayList<>();
        for (Long id : tipIds) {
            try {
                tipRouteDtos.add(tipService.findById(id));
            } catch (InstanceNotFoundException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(tipRouteDtos, HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/tips/sync", method = RequestMethod.POST)
    public ResponseEntity
    syncTIPs(@RequestBody List<TIPSyncDto> tipSyncDtos){
        tipService.syncTIPs(tipSyncDtos);
        return new ResponseEntity(HttpStatus.OK);
    }
}
