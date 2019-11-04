package ru.otus.homework;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.homework.dbservice.AccountService;
import ru.otus.homework.dbservice.DBService;
import ru.otus.homework.dbservice.UserService;
import ru.otus.homework.models.Account;
import ru.otus.homework.models.AddressDataSet;
import ru.otus.homework.models.PhoneDataSet;
import ru.otus.homework.models.User;

import java.util.ArrayList;
import java.util.List;

public class HibernateTest {
    private final DBService userService;
    private final DBService accountService;

    public  HibernateTest() {
        userService = new UserService();
        accountService = new AccountService();
    }

    @Test
    public void userCreate() {
        User user = new User();
        user.setName("Jager");
        user.setAge(30);
        user.setAddress(new AddressDataSet("xxx", user));

        List<PhoneDataSet> phones = new ArrayList<>();
        phones.add(new PhoneDataSet("+78888888888", user));
        phones.add(new PhoneDataSet("+79999999999", user));
        user.setPhones(phones);

        userService.create(user);
        long userId = user.getId();
        System.out.println("saved user with id = " + userId);

        User selected = (User) userService.load(userId);
        System.out.println("INSERTED: " + selected);

        Assertions.assertNotNull(selected);
    }

    @Test
    public void userUpdate() {
        User user = new User();
        user.setName("Jager");
        user.setAge(30);
        userService.create(user);
        String oldName = user.getName();

        user.setName("Jaget2019");
        userService.update(user);

        User selected = (User) userService.load(user.getId());
        System.out.println("INSERTED: " + selected);

        Assertions.assertNotEquals(oldName, selected.getName());
    }

    @Test
    public void userDelete() {
        User user = new User();
        user.setName("Jager");
        user.setAge(30);
        userService.create(user);
        long id = user.getId();

        userService.delete(user);
        User selected = (User) userService.load(id);

        Assertions.assertNull(selected);
    }

    @Test
    public void accountCreate() {
        Account account = new Account("main", 10, 1);

        accountService.create(account);
        long accountId = account.getId();
        System.out.println("saved account with id = " + accountId);

        Account selected = (Account) accountService.load(accountId);
        System.out.println("INSERTED: " + selected);

        Assertions.assertNotNull(selected);
    }
}
