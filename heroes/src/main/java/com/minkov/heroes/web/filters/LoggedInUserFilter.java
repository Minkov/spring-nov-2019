package com.minkov.heroes.web.filters;

import com.minkov.heroes.services.services.AuthenticatedUserService;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class LoggedInUserFilter implements Filter {
    private final AuthenticatedUserService authenticatedUserService;

    public LoggedInUserFilter(AuthenticatedUserService authenticatedUserService){
        this.authenticatedUserService = authenticatedUserService;
    }

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {
        String username = authenticatedUserService.getUsername();
        if(username.equals("anonymousUser")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        HttpSession session = ((HttpServletRequest)servletRequest).getSession();
        session.setAttribute("username", username);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
