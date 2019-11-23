package ru.otus.hw.webserver.service;

import ru.otus.hw.webserver.models.Account;
import ru.otus.hw.webserver.service.dbservice.AccountService;

import java.util.HashMap;
import java.util.Map;

public class AuthorizationServiceImpl implements AuthorizationService{
    private final Map<String, Account> sessionIdToAccount;
    private final AccountService accountService;

    public AuthorizationServiceImpl(AccountService accountService) {
        this.accountService = accountService;
        sessionIdToAccount = new HashMap<>();
    }

    @Override
    public String getSessionLogin(String sessionId) {

        return sessionIdToAccount.get(sessionId) == null
                ? null
                : sessionIdToAccount.get(sessionId).getLogin();
    }

    @Override
    public boolean isSessionExists(String sessionId) {

        return sessionIdToAccount.containsKey(sessionId);
    }

    @Override
    public boolean login(String sessionId, Account account) {
        boolean result = false;
        Account dbAccount = accountService.loginWithAccount(account);
        if (dbAccount != null) {
            addSession(sessionId, dbAccount);
            result = true;
        }
        return result;
    }

    private void addSession(String sessionId, Account account) {
        sessionIdToAccount.put(sessionId, account);
    }
}
