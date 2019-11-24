package ru.otus.hw.webserver.servlets;

import ru.otus.hw.webserver.server.UserSession;
import ru.otus.hw.webserver.service.AuthorizationService;
import ru.otus.hw.webserver.server.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static ru.otus.hw.webserver.server.PageGenerator.CONTENT_TYPE_FOR_RESPONSE;

public class HelloPageServlet extends HttpServlet {
    private static final String FILE_NAME = "index.html";

    private final AuthorizationService authorizationService;
    private final PageGenerator pageGenerator;

    public HelloPageServlet(AuthorizationService authorizationService, PageGenerator pageGenerator) throws IOException {
        this.authorizationService = authorizationService;
        this.pageGenerator = pageGenerator;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession httpSession =request.getSession();
        UserSession userSession = authorizationService.getUserSession(httpSession);

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.putAll(PageGenerator.setPageAuthorizationParameters(userSession));

        response.setContentType(CONTENT_TYPE_FOR_RESPONSE);
        response.getWriter().println(pageGenerator.getPage(FILE_NAME, pageVariables));
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
