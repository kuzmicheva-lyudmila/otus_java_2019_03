package ru.otus.homework.dbservice;

import ru.otus.homework.dao.Dao;
import ru.otus.homework.dao.UserDao;
import ru.otus.homework.models.User;


public class UserService implements DBService<User> {

    private Dao userDao;

    public UserService(boolean useCache) {
        userDao = new UserDao(useCache);
    }

    @Override
    public void create(User objectData) {
        userDao.create(objectData);
    }

    @Override
    public void update(User objectData) {
        userDao.update(objectData);
    }

    @Override
    public void delete(User objectData) {
        userDao.delete(objectData);
    }

    @Override
    public User load(long id) {
        return (User) userDao.load(id);
    }
}
