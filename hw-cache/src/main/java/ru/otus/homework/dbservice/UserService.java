package ru.otus.homework.dbservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework.cache.Cache;
import ru.otus.homework.dao.Dao;
import ru.otus.homework.dao.UserDaoImpl;
import ru.otus.homework.models.User;


public class UserService implements DBService<User> {
    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    private final Dao<User> userDao;
    private final Cache<String, User> cache;

    public UserService(Cache cache) {
        userDao = new UserDaoImpl();

        this.cache = cache;
        if (this.cache != null) {
            this.cache.addListener((key, value, action) -> logger.info("key: {}, value: {}, action: {}", key, value, action));
            for (User user : userDao.loadAll()) {
                this.cache.put(String.valueOf(user.getId()), user);
            }
        }
    }

    @Override
    public void create(User objectData) {
        userDao.create(objectData);

        if (this.cache != null) {
            cache.put(String.valueOf(objectData.getId()), objectData);
        }
    }

    @Override
    public void update(User objectData) {
        userDao.update(objectData);

        if (this.cache != null) {
            cache.put(String.valueOf(objectData.getId()), objectData);
        }
    }

    @Override
    public void delete(User objectData) {
        userDao.delete(objectData);

        if (this.cache != null) {
            cache.remove(String.valueOf(objectData.getId()));
        }
    }

    @Override
    public User load(long id) {
        User user = null;

        if (this.cache != null) {
            user = (User) cache.get(String.valueOf(id));
        }

        if (user == null) {
            user = userDao.load(id);
        }

        return user;
    }
}
