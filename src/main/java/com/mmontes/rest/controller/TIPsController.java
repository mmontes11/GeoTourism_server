package com.mmontes.rest.controller;

import com.mmontes.model.service.TIPService;
import com.mmontes.service.FacebookService;
import com.mmontes.util.GeometryUtils;
import com.mmontes.util.dto.FeatureSearchDto;
import com.mmontes.util.exception.GeometryParsingException;
import com.mmontes.util.exception.InstanceNotFoundException;
import com.vividsolutions.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TIPsController {

    @Autowired
    private TIPService tipService;

    @RequestMapping(value = "/tips", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<FeatureSearchDto>>
    find(@RequestParam(value = "bounds", required = false) String boundsWKT,
         @RequestParam(value = "types", required = false) List<Long> typeIds,
         @RequestParam(value = "cities", required = false) List<Long> cityIds,
         @RequestParam(value = "favouritedBy", required = false) Integer favouritedBy,
         @RequestHeader(value = "AuthorizationFB", required = false) String accessToken,
         @RequestParam(value = "facebookUserId", required = false) Long facebookUserId,
         @RequestParam(value = "friends", required = false) List<Long> friendsFacebookUserIds) {

        if (!FacebookService.validFBparams(accessToken,facebookUserId)){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Geometry bounds = null;
        try {
            if (boundsWKT != null) {
                bounds = GeometryUtils.geometryFromWKT(boundsWKT);
            }
        } catch (GeometryParsingException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (favouritedBy != null && favouritedBy != 0 && favouritedBy != 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<FeatureSearchDto> tips;
        try {
            tips = tipService.find(bounds, typeIds, cityIds, favouritedBy, facebookUserId, friendsFacebookUserIds);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tips, HttpStatus.OK);
    }
}
