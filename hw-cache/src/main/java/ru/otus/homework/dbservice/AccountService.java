package ru.otus.homework.dbservice;

import ru.otus.homework.dao.AccountDao;
import ru.otus.homework.dao.Dao;
import ru.otus.homework.models.Account;

public class AccountService implements DBService<Account> {
    private Dao accountDao;

    public AccountService() {
        accountDao = new AccountDao();
    }

    @Override
    public void create(Account objectData) {
        accountDao.create(objectData);
    }

    @Override
    public void update(Account objectData) {
        accountDao.update(objectData);
    }

    @Override
    public void delete(Account objectData) {
        accountDao.delete(objectData);
    }

    @Override
    public Account load(long id) {
        return (Account) accountDao.load(id);
    }
}
