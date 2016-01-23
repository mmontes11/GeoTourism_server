package com.mmontes.rest.filter;

import com.mmontes.service.FacebookService;
import com.mmontes.util.exception.FacebookServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SocialFilter extends GenericFilterBean {

    private FacebookService facebookService = new FacebookService();

    @Override
    public void doFilter(final ServletRequest req,
                         final ServletResponse res,
                         final FilterChain chain) throws IOException, ServletException {

        final HttpServletRequest request = (HttpServletRequest) req;
        if (!request.getMethod().equals("OPTIONS")) {
            String accessToken = request.getHeader("AuthorizationFB");
            String facebookUserIdString = request.getHeader("FacebookUserId");
            Long facebookUserId;
            try{
                facebookUserId = Long.parseLong(facebookUserIdString);
            } catch(NumberFormatException | NullPointerException e) {
                facebookUserId = null;
            }
            if (accessToken == null || facebookUserId == null) {
                final HttpServletResponse response = (HttpServletResponse) res;
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            facebookService.setParams(accessToken, facebookUserId);
            try {
                facebookService.validateAuth();
            } catch (FacebookServiceException e) {
                final HttpServletResponse response = (HttpServletResponse) res;
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }
        chain.doFilter(req, res);
    }
}