package ru.otus.homework.dbservice;

import org.hibernate.SessionFactory;
import ru.otus.homework.dao.AccountDao;
import ru.otus.homework.dao.Dao;
import ru.otus.homework.models.Account;

public class AccountService implements DBService<Account> {
    private Dao accountDao;

    public AccountService(SessionFactory sessionFactory) {
        accountDao = new AccountDao(sessionFactory);
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
