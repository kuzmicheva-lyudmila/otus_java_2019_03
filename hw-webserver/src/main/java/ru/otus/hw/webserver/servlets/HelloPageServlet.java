package ru.otus.hw.webserver.servlets;

import ru.otus.hw.webserver.service.AuthorizationService;
import ru.otus.hw.webserver.servlets.pagegenerator.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HelloPageServlet extends HttpServlet {
    private static final String FILE_NAME = "index.html";
    private static final String NOT_AUTHORIZED = "NOT AUTHORIZED";

    private final AuthorizationService authorizationService;
    private final PageGenerator pageGenerator;

    public HelloPageServlet(AuthorizationService authorizationService) throws IOException {
        this.authorizationService = authorizationService;
        this.pageGenerator = new PageGenerator();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String session = request.getSession().getId();
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("login", session == null ? authorizationService.getSessionLogin(session) : NOT_AUTHORIZED);

        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(pageGenerator.getPage(FILE_NAME, pageVariables));
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
