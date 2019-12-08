package ru.otus.hw.webserver;

import org.eclipse.jetty.server.Server;
import ru.otus.hw.webserver.server.LocalServer;

public class MainClass {

    public static void main(String[] args) throws Exception {
        Server server = LocalServer.runServer();

        server.start();
        server.join();
    }
}
