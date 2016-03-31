package com.mmontes.rest.controller;

import com.mmontes.model.service.AdminService;
import com.mmontes.model.service.ConfigService;
import com.mmontes.rest.request.AdminLoginRequest;
import com.mmontes.rest.request.OSMtypeRequest;
import com.mmontes.rest.response.ResponseFactory;
import com.mmontes.util.PrivateConstants;
import com.mmontes.util.dto.ConfigDto;
import com.mmontes.util.dto.IDnameDto;
import com.mmontes.util.dto.OSMTypeDto;
import com.mmontes.util.exception.DuplicateInstanceException;
import com.mmontes.util.exception.InstanceNotFoundException;
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
    getConfig() {
        return new ResponseEntity<>(configService.getConfig(), HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/config/bbox", method = RequestMethod.GET)
    public ResponseEntity
    getBBox() {
        String bbox = configService.getBBox();
        return new ResponseEntity<>(ResponseFactory.getCustomJSON("bbox", bbox), HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/config/osmtypes", method = RequestMethod.GET)
    public ResponseEntity<List<OSMTypeDto>>
    getOSMtypes(@RequestParam(required = false) Boolean tipTypeSetted) {
        List<OSMTypeDto> osmTypes = configService.getOSMTypes((tipTypeSetted != null && tipTypeSetted));
        return new ResponseEntity<>(osmTypes, HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/config/osmtype", method = RequestMethod.POST)
    public ResponseEntity<OSMTypeDto>
    createOSMType(@RequestBody OSMtypeRequest osmTypeRequest){
        if (osmTypeRequest.getTipTypeId() == null || osmTypeRequest.getOsmValueId() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            OSMTypeDto osmTypeDto = configService.createOSMType(osmTypeRequest.getOsmValueId(),osmTypeRequest.getTipTypeId());
            return new ResponseEntity<>(osmTypeDto,HttpStatus.CREATED);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (DuplicateInstanceException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/admin/config/osmtype/{OSMTypeId}", method = RequestMethod.PUT)
    public ResponseEntity<OSMTypeDto>
    updateOSMType(@PathVariable Long OSMTypeId,@RequestBody OSMtypeRequest osmTypeRequest){
        if (osmTypeRequest.getTipTypeId() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            OSMTypeDto osmTypeDto = configService.updateOSMType(OSMTypeId,osmTypeRequest.getTipTypeId());
            return new ResponseEntity<>(osmTypeDto,HttpStatus.OK);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/admin/config/osmtype/{OSMTypeId}", method = RequestMethod.DELETE)
    public ResponseEntity<OSMTypeDto>
    deleteOSMType(@PathVariable Long OSMTypeId){
        if (OSMTypeId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            configService.deleteOSMType(OSMTypeId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @RequestMapping(value = "/admin/config/osmkeys", method = RequestMethod.GET)
    public ResponseEntity<List<IDnameDto>>
    getOSMkeys() {
        List<IDnameDto> osmKeys = configService.getOSMKeys();
        return new ResponseEntity<>(osmKeys, HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/config/osmkey/{OSMKey}/osmvalues", method = RequestMethod.GET)
    public ResponseEntity<List<IDnameDto>>
    getOSMvalues(@PathVariable String OSMKey) {
        List<IDnameDto> osmValues = configService.findOSMValuesByOSMKey(OSMKey);
        return new ResponseEntity<>(osmValues, HttpStatus.OK);
    }
}
