package com.mmontes.rest.filter;

import com.mmontes.service.FacebookService;
import com.mmontes.util.exception.FacebookServiceException;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SocialFilter extends GenericFilterBean {

    @Override
    public void doFilter(final ServletRequest req,
                         final ServletResponse res,
                         final FilterChain chain) throws IOException, ServletException {

        final HttpServletRequest request = (HttpServletRequest) req;
        if (!request.getMethod().equals("OPTIONS")) {
            final String accessToken = request.getHeader("AuthorizationFB");
            final Long facebookUserId = Long.valueOf(request.getParameter("facebookUserId"));
            if (accessToken == null || facebookUserId == null) {
                final HttpServletResponse response = (HttpServletResponse) res;
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            FacebookService fs = new FacebookService(accessToken,facebookUserId);
            try {
                fs.validateAuth();
            } catch (FacebookServiceException e) {
                final HttpServletResponse response = (HttpServletResponse) res;
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }
        chain.doFilter(req, res);
    }
}