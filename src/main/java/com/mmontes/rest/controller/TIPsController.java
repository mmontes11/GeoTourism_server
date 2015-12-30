package com.mmontes.rest.controller;

import com.mmontes.model.service.TIPService;
import com.mmontes.service.FacebookService;
import com.mmontes.util.GeometryUtils;
import com.mmontes.util.dto.FeatureSearchDto;
import com.mmontes.util.exception.FacebookServiceException;
import com.mmontes.util.exception.GeometryParsingException;
import com.mmontes.util.exception.InstanceNotFoundException;
import com.vividsolutions.jts.geom.Polygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class TIPsController {

    @Autowired
    private TIPService tipService;

    @Autowired
    private FacebookService facebookService;

    @RequestMapping(value = "/tips", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<FeatureSearchDto>>
    find(@RequestParam(value = "bounds", required = false) String boundsWKT,
         @RequestParam(value = "types", required = false) List<Long> typeIds,
         @RequestParam(value = "cities", required = false) List<Long> cityIds,
         @RequestParam(value = "favouritedBy", required = false) Integer favouritedBy,
         @RequestHeader(value="AuthorizationFB", required = false) String accessToken,
         @RequestParam(value = "facebookUserId", required = false) Long facebookUserId,
         @RequestParam(value = "friends", required = false) List<Long> friendsFacebookUserIds) {

        if (accessToken != null && facebookUserId != null){
            facebookService.setParams(accessToken, facebookUserId);
            try {
                facebookService.validateAuth();
            } catch (FacebookServiceException e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        Polygon bounds = null;
        try {
            if (boundsWKT != null) {
                bounds = (Polygon) GeometryUtils.geometryFromWKT(boundsWKT);
            }
        } catch (GeometryParsingException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (favouritedBy != null && favouritedBy != 0 && favouritedBy != 1){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<FeatureSearchDto> tips = null;
        try {
            tips = tipService.find(bounds, typeIds, cityIds, favouritedBy, facebookUserId, friendsFacebookUserIds);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tips, HttpStatus.OK);
    }
}
