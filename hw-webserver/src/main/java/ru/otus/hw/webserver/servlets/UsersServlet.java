package ru.otus.hw.webserver.servlets;

import ru.otus.hw.webserver.models.AddressDataSet;
import ru.otus.hw.webserver.models.PhoneDataSet;
import ru.otus.hw.webserver.models.User;
import ru.otus.hw.webserver.service.AuthorizationService;
import ru.otus.hw.webserver.service.dbservice.UserService;
import ru.otus.hw.webserver.server.PageGenerator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static ru.otus.hw.webserver.server.LocalServer.PATH_ADMIN;
import static ru.otus.hw.webserver.server.PageGenerator.*;

public class UsersServlet extends HttpServlet {
    private static final String FILE_NAME = "admin_page.html";

    private final UserService userService;
    private final AuthorizationService authorizationService;
    private final PageGenerator pageGenerator;

    public UsersServlet(AuthorizationService authorizationService, UserService userService, PageGenerator pageGenerator) throws IOException {
        this.authorizationService = authorizationService;
        this.userService = userService;
        this.pageGenerator = pageGenerator;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String users = userService.loadAll().toString();
        request.setAttribute(PAGE_PARAMETER_USERS, users);
        RequestDispatcher dispatcher = request.getRequestDispatcher(PATH_ADMIN);
        dispatcher.forward(request, response);
    }

    @Override
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
        user.setPhones(Collections.singletonList(new PhoneDataSet(request.getParameter(PAGE_PARAMETER_SET_USER_PHONE), user)));
        userService.create(user);
    }
}
