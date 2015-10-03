package com.mmontes.rest.security;

import com.mmontes.util.Constants;
import org.springframework.web.filter.GenericFilterBean;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

public class AdminSecurityFilter extends GenericFilterBean{

    @Override
    public void doFilter(final ServletRequest req,
                         final ServletResponse res,
                         final FilterChain chain) throws IOException, ServletException {

        final HttpServletRequest request = (HttpServletRequest) req;
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ServletException("Missing or invalid Authorization header.");
        }
        final String token = authHeader.substring("Bearer ".length());
        try {
            Jwts.parser().setSigningKey(Constants.TOKEN_SECRET_KEY).parse(token).getBody();
        }
        catch (final SignatureException e) {
            final HttpServletResponse response = (HttpServletResponse) res;
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        chain.doFilter(req, res);
    }
}
