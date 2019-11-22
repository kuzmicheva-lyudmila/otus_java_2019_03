package ru.otus.hw.webserver.service;

import ru.otus.hw.webserver.models.Account;

public interface AuthorizationService {
    void addSession (String sessionId, Account account);
    void deleteSession(String sessionId);
    String getSessionLogin(String sessionId);
    boolean isSessionExists(String sessionId);
    boolean checkAndAddAccount(String sessionId, Account account);
}
