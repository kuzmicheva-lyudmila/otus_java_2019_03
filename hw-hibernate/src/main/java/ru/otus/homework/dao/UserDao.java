package ru.otus.homework.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.otus.homework.models.User;

public class UserDao implements Dao<User> {

    public UserDao() {
    }

    @Override
    public void create(User objectData) {
        try (Session session = HibernateSession.getSessionFactory().openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.save(objectData);
            transaction.commit();
        }
    }

    @Override
    public void update(User objectData) {
        try (Session session = HibernateSession.getSessionFactory().openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.update(objectData);
            transaction.commit();
        }
    }


    @Override
    public void delete(User objectData) {
        try (Session session = HibernateSession.getSessionFactory().openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.delete(objectData);
            transaction.commit();
        }
    }

    @Override
    public User load(long id) {
        try (Session session = HibernateSession.getSessionFactory().openSession()) {
            return session.get(User.class, id);
        }
    }
}
