package ru.otus.homework.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework.cache.HWCache;
import ru.otus.homework.cache.HWListener;
import ru.otus.homework.cache.MyCache;
import ru.otus.homework.models.User;

import javax.persistence.EntityManager;
import java.util.List;

public class UserDao implements Dao<User> {
    private static Logger logger = LoggerFactory.getLogger(UserDao.class);

    private final boolean useCache;
    private final HWCache<String, User> cache;

    public UserDao(boolean useCache) {
        this.useCache = useCache;
        if (this.useCache) {
            cache = new MyCache<>();
            cache.addListener((key, value, action) -> logger.info("key: {}, value: {}, action: {}",  key, value, action));

            for (User user: loadAll()) {
                cache.put(String.valueOf(user.getId()), user);
            }
        } else {
            cache = null;
        }
    }

    @Override
    public void create(User objectData) {
        try (Session session = HibernateSession.getSessionFactory().openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.save(objectData);
            transaction.commit();
            logger.info("DB created user: {}", objectData);

            if (useCache) {
                cache.put(String.valueOf(objectData.getId()), objectData);
            }
        }
    }

    @Override
    public void update(User objectData) {
        try (Session session = HibernateSession.getSessionFactory().openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.update(objectData);
            transaction.commit();
            logger.info("DB updated user: {}", objectData);

            if (useCache) {
                cache.put(String.valueOf(objectData.getId()), objectData);
            }
        }
    }

    @Override
    public void delete(User objectData) {
        try (Session session = HibernateSession.getSessionFactory().openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.delete(objectData);
            transaction.commit();
            logger.info("DB deleted user: {}", objectData);

            if (useCache) {
                cache.remove(String.valueOf(objectData.getId()));
            }
        }
    }

    @Override
    public User load(long id) {
        User user = null;

        if (useCache) {
            user = cache.get(String.valueOf(id));
        } else {
            try (Session session = HibernateSession.getSessionFactory().openSession()) {
                user = session.get(User.class, id);
                logger.info("DB selected user: {}", user);
            }
        }

        return user;
    }

    private List<User> loadAll() {
        EntityManager entityManager = HibernateSession.getSessionFactory().createEntityManager();
        List<User> selectedUsers = entityManager.createQuery("select u from User u", User.class)
                .getResultList();

        return selectedUsers;
    }
}
