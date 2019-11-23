package ru.otus.hw.webserver.servlets;

import ru.otus.hw.webserver.models.Account;
import ru.otus.hw.webserver.service.AuthorizationService;
import ru.otus.hw.webserver.servlets.pagegenerator.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AdminServlet extends HttpServlet {
    private static final String FILE_NAME = "admin_page.html";
    private static final String NOT_AUTHORIZED = "NOT AUTHORIZED";

    private final AuthorizationService authorizationService;
    private final PageGenerator pageGenerator;

    public AdminServlet(AuthorizationService authorizationService) throws IOException {
        this.authorizationService = authorizationService;
        this.pageGenerator = new PageGenerator();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String session = request.getSession().getId();
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("login",
                authorizationService.getSessionLogin(session) == null
                    ? NOT_AUTHORIZED
                    : authorizationService.getSessionLogin(session)
        );
        pageVariables.put("users", "");

        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(pageGenerator.getPage(FILE_NAME, pageVariables));
        response.setStatus(HttpServletResponse.SC_OK);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {

        String login= request.getParameter("login");
        String password = request.getParameter("password");
        Account account = new Account(login, password);

        String session = request.getSession().getId();
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("users", "");

        boolean result = false;
        if (!authorizationService.isSessionExists(session)) {
            result = authorizationService.login(request.getSession().getId(), account);
        }
        if (!result) {
            pageVariables.put("login", NOT_AUTHORIZED);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            pageVariables.put("login", login);
            response.setStatus(HttpServletResponse.SC_OK);
        }
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(pageGenerator.getPage(FILE_NAME, pageVariables));
    }
}
