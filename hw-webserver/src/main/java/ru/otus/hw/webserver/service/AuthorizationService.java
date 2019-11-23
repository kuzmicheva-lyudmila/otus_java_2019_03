package ru.otus.hw.webserver.service;

import ru.otus.hw.webserver.models.Account;

public interface AuthorizationService {
    String getSessionLogin(String sessionId);
    boolean isSessionExists(String sessionId);
    boolean login(String sessionId, Account account);
}
