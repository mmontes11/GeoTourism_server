package com.mmontes.rest.controller;

import com.mmontes.model.service.AdminService;
import com.mmontes.rest.request.AdminLoginRequest;
import com.mmontes.rest.response.AdminLoginResponse;
import com.mmontes.util.Constants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import java.util.Calendar;

@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/logIn", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdminLoginResponse>
    login(@RequestBody  AdminLoginRequest adminLogin) throws ServletException {

        if (adminLogin.getUsername() == null || adminLogin.getPassword() == null ||
                !adminService.checkPassword(adminLogin.getUsername(),adminLogin.getPassword())){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Calendar now = Calendar.getInstance();
        Calendar expiration = Calendar.getInstance();
        expiration.setTimeInMillis(now.getTimeInMillis() + Constants.TOKEN_EXPIRATION_IN_MILIS);

        String token =
        Jwts.builder()
                .setSubject(adminLogin.getUsername())
                .setIssuedAt(now.getTime())
                .setExpiration(expiration.getTime())
                .signWith(SignatureAlgorithm.HS512, Constants.TOKEN_SECRET_KEY)
                .compact();

        return new ResponseEntity<>(new AdminLoginResponse(token), HttpStatus.OK);
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ResponseEntity
    validateToken(){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
