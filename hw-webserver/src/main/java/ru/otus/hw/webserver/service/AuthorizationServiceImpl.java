package ru.otus.hw.webserver.service;

import ru.otus.hw.webserver.models.Account;
import ru.otus.hw.webserver.server.UserSession;
import ru.otus.hw.webserver.service.dbservice.AccountService;

import javax.servlet.http.HttpSession;
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
    public UserSession getUserSession(HttpSession httpSession) {
        if (httpSession == null) {
            return null;
        }

        String id = httpSession.getId();
        return sessionIdToAccount.get(id) == null
                ? null
                : new UserSession(id, sessionIdToAccount.get(id).getLogin());
    }

    @Override
    public boolean isSessionExists(UserSession userSession) {
        return (userSession != null) && isSessionExists(userSession.getId());
    }

    private boolean isSessionExists(String sessionId) {
        return sessionIdToAccount.containsKey(sessionId);
    }

    @Override
    public UserSession login(String sessionId, Account account) {
        if (isSessionExists(sessionId)) {
            removeSession(sessionId);
        }

        boolean result = false;
        Account dbAccount = accountService.loginWithAccount(account);
        if (dbAccount != null) {
            addSession(sessionId, dbAccount);
            result = true;
        }
        return result ? new UserSession(sessionId, dbAccount.getLogin()) : null;
    }

    private void addSession(String sessionId, Account account) {
        sessionIdToAccount.put(sessionId, account);
    }

    private void removeSession(String sessionId) {
        sessionIdToAccount.remove(sessionId);
    }
}
