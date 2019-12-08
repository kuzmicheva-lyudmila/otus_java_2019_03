package ru.otus.hw.webserver.servlets;

import ru.otus.hw.webserver.models.Account;
import ru.otus.hw.webserver.server.PageGenerator;
import ru.otus.hw.webserver.server.UserSession;
import ru.otus.hw.webserver.service.AuthorizationService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static ru.otus.hw.webserver.server.LocalServer.PATH_ADMIN;
import static ru.otus.hw.webserver.server.PageGenerator.*;

public class LoginServlet extends HttpServlet {
    private static final String FILE_NAME = "login_page.html";

    private final AuthorizationService authorizationService;
    private final PageGenerator pageGenerator;

    public LoginServlet(AuthorizationService authorizationService, PageGenerator pageGenerator) {

        this.authorizationService = authorizationService;
        this.pageGenerator = pageGenerator;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        UserSession userSession = authorizationService.getUserSession(request.getSession());
        Map<String, Object> pageVariables = new HashMap<>(PageGenerator.setPageAuthorizationParameters(userSession));

        response.setContentType(CONTENT_TYPE_FOR_RESPONSE);
        response.getWriter().println(pageGenerator.getPage(FILE_NAME, pageVariables));
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String login= request.getParameter(PAGE_PARAMETER_SET_LOGIN);
        String password = request.getParameter(PAGE_PARAMETER_SET_PASSWORD);
        Account account = new Account(login, password);

        HttpSession httpSession = request.getSession();
        authorizationService.login(httpSession.getId(), account);

        response.sendRedirect(PATH_ADMIN);
    }

}
