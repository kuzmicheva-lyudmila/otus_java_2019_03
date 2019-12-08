package ru.otus.hw.webserver.server;

public class UserSession {

    private final String id;
    private final String login;

    public UserSession(String id, String login) {
        this.id = id;
        this.login = login;
    }

    public String getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }
}
