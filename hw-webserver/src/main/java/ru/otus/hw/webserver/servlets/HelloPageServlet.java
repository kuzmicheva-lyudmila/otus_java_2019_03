package ru.otus.hw.webserver.servlets;

import ru.otus.hw.webserver.server.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

import static ru.otus.hw.webserver.server.PageGenerator.CONTENT_TYPE_FOR_RESPONSE;

public class HelloPageServlet extends HttpServlet {
    private static final String FILE_NAME = "/static/index.html";

    private final PageGenerator pageGenerator;

    public HelloPageServlet(PageGenerator pageGenerator) {
        this.pageGenerator = pageGenerator;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType(CONTENT_TYPE_FOR_RESPONSE);
        response.getWriter().println(pageGenerator.getPage(FILE_NAME, new HashMap<>()));
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
