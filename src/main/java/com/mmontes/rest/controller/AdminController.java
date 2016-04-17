package com.mmontes.rest.controller;

import com.mmontes.model.entity.OSM.OSMType;
import com.mmontes.model.service.AdminService;
import com.mmontes.model.service.ConfigService;
import com.mmontes.rest.request.AdminLoginRequest;
import com.mmontes.rest.request.ConfigBBoxRequest;
import com.mmontes.rest.response.ResponseFactory;
import com.mmontes.util.GeometryUtils;
import com.mmontes.util.PrivateConstants;
import com.mmontes.util.dto.*;
import com.mmontes.util.exception.DuplicateInstanceException;
import com.mmontes.util.exception.GeometryParsingException;
import com.mmontes.util.exception.InstanceNotFoundException;
import com.vividsolutions.jts.geom.Geometry;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import java.util.Calendar;
import java.util.List;

@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private ConfigService configService;

    @RequestMapping(value = "/logIn", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity
    login(@RequestBody AdminLoginRequest adminLogin) throws ServletException {

        if (adminLogin.getUsername() == null || adminLogin.getPassword() == null ||
                !adminService.checkPassword(adminLogin.getUsername(), adminLogin.getPassword())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Calendar now = Calendar.getInstance();
        Calendar expiration = Calendar.getInstance();
        int expirationOffsetMilis;
        if (adminLogin.getUsername().equals("etl")) {
            expirationOffsetMilis = PrivateConstants.TOKEN_ETL_EXPIRATION_IN_MILIS;
        } else {
            expirationOffsetMilis = PrivateConstants.TOKEN_EXPIRATION_IN_MILIS;
        }
        expiration.setTimeInMillis(now.getTimeInMillis() + expirationOffsetMilis);

        String token =
                Jwts.builder()
                        .setSubject(adminLogin.getUsername())
                        .setIssuedAt(now.getTime())
                        .setExpiration(expiration.getTime())
                        .signWith(SignatureAlgorithm.HS512, PrivateConstants.TOKEN_SECRET_KEY)
                        .compact();

        return new ResponseEntity<>(ResponseFactory.getCustomJSON("token", token), HttpStatus.OK);
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ResponseEntity
    validateToken() {
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(value = "/admin/config", method = RequestMethod.GET)
    public ResponseEntity<ConfigDto>
    getConfig(@RequestParam(value = "bboxMin", required = false) Boolean BBoxMin,
              @RequestParam(value = "hasTIPtype", required = false) Boolean hasTIPtype) {
        BBoxMin = BBoxMin != null ? BBoxMin : false;
        hasTIPtype = hasTIPtype != null ? hasTIPtype : false;
        return new ResponseEntity<>(configService.getConfig(BBoxMin, hasTIPtype), HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/config/bbox", method = RequestMethod.GET)
    public ResponseEntity
    getConfigBBox() {
        return new ResponseEntity<>(ResponseFactory.getCustomJSON("geom",configService.getBBox()), HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/config/osmtypes", method = RequestMethod.GET)
    public ResponseEntity<List<OSMTypeDto>>
    getConfigOSMtypes(@RequestParam(value = "hasTIPtype",required = false)Boolean hasTIPtypeRequest) {
        boolean hasTIPtype = hasTIPtypeRequest != null? hasTIPtypeRequest : false;
        return new ResponseEntity<>(configService.getOSMtypes(hasTIPtype), HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/config/osmtype/{OSMType}", method = RequestMethod.GET)
    public ResponseEntity
    checkOSMtypeByKeyValue(@PathVariable String OSMType,@RequestParam(value = "hasTIPtype",required = false)Boolean hasTIPtypeRequest) {
        boolean hasTIPtype = hasTIPtypeRequest != null? hasTIPtypeRequest : false;
        if (OSMType == null || OSMType.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            configService.getOSMtypeByValue(OSMType, hasTIPtype);
            return new ResponseEntity(HttpStatus.OK);
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (DuplicateInstanceException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/admin/config/bbox", method = RequestMethod.POST)
    public ResponseEntity
    upsertBBox(@RequestBody ConfigBBoxRequest configBBoxRequest) {
        Geometry geom;
        if (configBBoxRequest.getGeom() != null) {
            try {
                geom = GeometryUtils.geometryFromWKT(configBBoxRequest.getGeom());
            } catch (GeometryParsingException e) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        configService.upsertConfigBBox(geom);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/config/osmkeys", method = RequestMethod.GET)
    public ResponseEntity<List<String>>
    getOSMKeys() {
        return new ResponseEntity<>(configService.getOSMKeys(), HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/config/osmkey/{OSMKey}/osmtypes", method = RequestMethod.GET)
    public ResponseEntity<List<String>>
    getOSMTypes(@PathVariable String OSMKey) {
        return new ResponseEntity<>(configService.findOSMTypesByOSMKey(OSMKey), HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/config/tips", method = RequestMethod.GET)
    public ResponseEntity<List<TIPReviewDto>>
    getUnreviewedTIPs() {
        return new ResponseEntity<>(configService.findUnreviewedTIPs(), HttpStatus.OK);
    }
}
