package ru.otus.hw.webserver.servlets;

import ru.otus.hw.webserver.server.UserSession;
import ru.otus.hw.webserver.service.AuthorizationService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthorizationFilter implements Filter {
    private final String REQUESTED_RESOURCE_LOG_MSG = "Requested Resource: ";

    private final AuthorizationService authorizationService;
    private ServletContext context;

    public AuthorizationFilter(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @Override
    public void init(FilterConfig filterConfig) {
        this.context = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        String uri = req.getRequestURI();
        this.context.log(REQUESTED_RESOURCE_LOG_MSG + uri);

        HttpSession httpSession = req.getSession(false);
        UserSession userSession = authorizationService.getUserSession(httpSession);
        if (!authorizationService.isSessionExists(userSession)) {
            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}