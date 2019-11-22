package ru.otus.hw.webserver.servlets;

import ru.otus.hw.webserver.models.AddressDataSet;
import ru.otus.hw.webserver.models.PhoneDataSet;
import ru.otus.hw.webserver.models.User;
import ru.otus.hw.webserver.service.AuthorizationService;
import ru.otus.hw.webserver.service.dbservice.UserService;
import ru.otus.hw.webserver.servlets.pagegenerator.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class UsersServlet extends HttpServlet {
    private static final String FILE_NAME = "admin_page.html";
    private static final String NOT_AUTHORIZED = "NOT AUTHORIZED";

    private final UserService userService;
    private final AuthorizationService authorizationService;
    private final PageGenerator pageGenerator;

    public UsersServlet(AuthorizationService authorizationService, UserService userService) throws IOException {
        this.authorizationService = authorizationService;
        this.userService = userService;
        this.pageGenerator = new PageGenerator();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String session = request.getSession(false).getId();
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put(
                "login",
                authorizationService.getSessionLogin(session).isEmpty()
                        ? NOT_AUTHORIZED
                        : authorizationService.getSessionLogin(session)
        );
        pageVariables.put("users", userService.loadAll().toString());

        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(pageGenerator.getPage(FILE_NAME, pageVariables));
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        User user = new User();
        user.setName(request.getParameter("userName"));
        user.setAge(Integer.parseInt(request.getParameter("userAge")));
        user.setAddress(new AddressDataSet(request.getParameter("userAddress"), user));
        user.setPhones(Collections.singletonList(new PhoneDataSet(request.getParameter("userPhone"), user)));
        userService.create(user);

        String session = request.getSession(false).getId();
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
}
