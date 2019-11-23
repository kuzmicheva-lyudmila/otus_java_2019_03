package ru.otus.hw.webserver;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.hw.webserver.dao.AccountDaoImpl;
import ru.otus.hw.webserver.dao.UserDaoImpl;
import ru.otus.hw.webserver.models.User;
import ru.otus.hw.webserver.service.AuthorizationService;
import ru.otus.hw.webserver.service.AuthorizationServiceImpl;
import ru.otus.hw.webserver.service.cache.CacheImpl;
import ru.otus.hw.webserver.service.dbservice.AccountService;
import ru.otus.hw.webserver.service.dbservice.AccountServiceImpl;
import ru.otus.hw.webserver.service.dbservice.UserService;
import ru.otus.hw.webserver.service.dbservice.UserServiceImpl;
import ru.otus.hw.webserver.servlets.AdminServlet;
import ru.otus.hw.webserver.servlets.AuthorizationFilter;
import ru.otus.hw.webserver.servlets.HelloPageServlet;
import ru.otus.hw.webserver.servlets.UsersServlet;

public class MainClass {
    private static final int PORT = 8080;

    public static void main(String[] args) throws Exception {
        new MainClass().start();
    }

    private void start() throws Exception {
        AccountService accountService = new AccountServiceImpl(new AccountDaoImpl());
        AuthorizationService authorizationService = new AuthorizationServiceImpl(accountService);
        UserService userService = new UserServiceImpl(new CacheImpl<String, User>(), new UserDaoImpl());

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new AdminServlet(authorizationService)), "/admin");
        context.addServlet(new ServletHolder(new UsersServlet(authorizationService, userService)), "/users");
        context.addServlet(new ServletHolder(new HelloPageServlet(authorizationService)),"/*");
        context.addFilter(new FilterHolder(new AuthorizationFilter(authorizationService)), "/users", null);

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(context));

        server.start();
        server.join();
    }
}
