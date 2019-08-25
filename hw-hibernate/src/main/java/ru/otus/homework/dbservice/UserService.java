package ru.otus.homework.dbservice;

import org.hibernate.SessionFactory;
import ru.otus.homework.dao.Dao;
import ru.otus.homework.dao.UserDao;
import ru.otus.homework.models.User;


public class UserService implements DBService<User> {

    private Dao userDao;

    public UserService(SessionFactory sessionFactory) {
        userDao = new UserDao(sessionFactory);
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
