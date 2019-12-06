package ru.otus.hw.webserver.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
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
import ru.otus.hw.webserver.servlets.*;

import java.io.IOException;

public class LocalServer {
    public static final String PATH_ADMIN = "/admin";
    public static final String PATH_LOGIN = "/login";

    private static final String PATH_DEFAULT = "/*";
    private static final String STATIC = "/web-page/static";

    private static final int PORT = 8080;

    public static Server runServer() {

        PageGenerator pageGenerator = new PageGenerator();
        AccountService accountService = new AccountServiceImpl(new AccountDaoImpl());
        AuthorizationService authorizationService = new AuthorizationServiceImpl(accountService);
        UserService userService = new UserServiceImpl(new CacheImpl(), new UserDaoImpl());

        ResourceHandler resourceHandler = new ResourceHandler();
        Resource resource = Resource.newClassPathResource(STATIC);
        resourceHandler.setBaseResource(resource);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(
                new ServletHolder(new AdminServlet(authorizationService, userService, pageGenerator)),
                PATH_ADMIN
        );
        context.addServlet(new ServletHolder(new LoginServlet(authorizationService, pageGenerator)), PATH_LOGIN);
        context.addFilter(new FilterHolder(new AuthorizationFilter(authorizationService)), PATH_ADMIN, null);
        context.addServlet(new ServletHolder(new HelloPageServlet(pageGenerator)), PATH_DEFAULT);

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, context));
        return server;
    }
}
