package com.mmontes.rest.controller;

import com.mmontes.model.service.AdminService;
import com.mmontes.model.service.ConfigService;
import com.mmontes.rest.request.AdminLoginRequest;
import com.mmontes.rest.request.ConfigBBoxRequest;
import com.mmontes.rest.response.ResponseFactory;
import com.mmontes.util.GeometryUtils;
import com.mmontes.util.PrivateConstants;
import com.mmontes.util.dto.ConfigDto;
import com.mmontes.util.dto.IDnameDto;
import com.mmontes.util.exception.GeometryParsingException;
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

    @RequestMapping(value = "/admin/config/bbox", method = RequestMethod.POST)
    public ResponseEntity
    upsertBBox(@RequestBody ConfigBBoxRequest configBBoxRequest) {
        Geometry bbox;
        if (configBBoxRequest.getBbox() != null) {
            try {
                bbox = GeometryUtils.geometryFromWKT(configBBoxRequest.getBbox());
            } catch (GeometryParsingException e) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        configService.upsertConfigBBox(bbox);
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
}
