package ru.otus.hw.webserver.service;

import ru.otus.hw.webserver.models.Account;
import ru.otus.hw.webserver.server.UserSession;

import javax.servlet.http.HttpSession;

public interface AuthorizationService {
    UserSession getUserSession(HttpSession httpSession);
    boolean isSessionExists(UserSession userSession);
    UserSession login(String sessionId, Account account);
}
