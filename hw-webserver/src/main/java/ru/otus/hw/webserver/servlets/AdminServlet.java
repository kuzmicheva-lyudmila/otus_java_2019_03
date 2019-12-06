package ru.otus.hw.webserver.servlets;

import ru.otus.hw.webserver.models.AddressDataSet;
import ru.otus.hw.webserver.models.PhoneDataSet;
import ru.otus.hw.webserver.models.User;
import ru.otus.hw.webserver.server.UserSession;
import ru.otus.hw.webserver.service.AuthorizationService;
import ru.otus.hw.webserver.server.PageGenerator;
import ru.otus.hw.webserver.service.dbservice.UserService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static ru.otus.hw.webserver.server.LocalServer.PATH_ADMIN;
import static ru.otus.hw.webserver.server.PageGenerator.*;

public class AdminServlet extends HttpServlet {
    private static final String FILE_NAME = "admin_page.html";

    private final AuthorizationService authorizationService;
    private final UserService userService;
    private final PageGenerator pageGenerator;

    public AdminServlet(
            AuthorizationService authorizationService,
            UserService userService,
            PageGenerator pageGenerator
    ) {
        this.authorizationService = authorizationService;
        this.userService = userService;
        this.pageGenerator = pageGenerator;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        UserSession userSession = authorizationService.getUserSession(request.getSession());
        Map<String, Object> pageVariables = new HashMap<>(PageGenerator.setPageAuthorizationParameters(userSession));
        pageVariables.put(PAGE_PARAMETER_USERS, userService.loadAll().toString());

        response.setContentType(CONTENT_TYPE_FOR_RESPONSE);
        response.getWriter().println(pageGenerator.getPage(FILE_NAME, pageVariables));
        response.setStatus(HttpServletResponse.SC_OK);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        createUser(request);
        response.sendRedirect(PATH_ADMIN);
    }

    private void createUser(HttpServletRequest request) {
        User user = new User();
        user.setName(request.getParameter(PAGE_PARAMETER_SET_USER_NAME));
        user.setAge(Integer.parseInt(request.getParameter(PAGE_PARAMETER_SET_USER_AGE)));
        user.setAddress(new AddressDataSet(request.getParameter(PAGE_PARAMETER_SET_USER_ADDRESS), user));
        user.setPhones(
                Collections.singletonList(
                        new PhoneDataSet(request.getParameter(PAGE_PARAMETER_SET_USER_PHONE), user)
                )
        );
        userService.create(user);
    }

}
