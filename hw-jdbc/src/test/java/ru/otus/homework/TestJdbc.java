package ru.otus.homework;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.otus.homework.dao.Account;
import ru.otus.homework.dao.User;
import ru.otus.homework.dbservice.DBService;
import ru.otus.homework.dbservice.DBServiceImpl;
import ru.otus.homework.dbservice.DataSourceH2;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestJdbc {
    private final DataSource dataSource = new DataSourceH2();

    @Test
    public void usedUser() throws SQLException {
        createUserTable();
        DBService<User> dbServiceUser = new DBServiceImpl<>(dataSource, User.class);
        long idUser;

        idUser = dbServiceUser.create(new User("Maria", 25));
        System.out.println("inserted user id = " + idUser);

        idUser = dbServiceUser.update(new User(1, "Marina", 25));
        System.out.println("updated user id = " + idUser);

        User selectedUser = dbServiceUser.load(idUser);
        System.out.println("selected user id = " + idUser + ": " + selectedUser);

        assertEquals(selectedUser.getName(), "Marina");
    }

    @Test
    public void usedAccount() throws SQLException {
        createAccountTable();
        DBService<Account> dbServiceAccount = new DBServiceImpl<>(dataSource, Account.class);
        long idAccount;

        idAccount = dbServiceAccount.create(new Account("main", 1000, 1));
        System.out.println("inserted account id = " + idAccount);

        idAccount = dbServiceAccount.createOrUpdate(new Account(2, "main", 1000, 2));
        System.out.println("merged account id = " + idAccount);

        Account selectedAccount = dbServiceAccount.load(idAccount);
        System.out.println("selected account id = " + idAccount + ": " + selectedAccount);

        assertEquals(selectedAccount.getLevel(), 2);
    }

    private void createUserTable() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pst = connection.prepareStatement("create table user(id bigint(20) NOT NULL auto_increment, name varchar(255), age int(3))")) {
            pst.executeUpdate();
        }
        System.out.println("table User created");
    }

    private void createAccountTable() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pst = connection.prepareStatement("create table account(no bigint(20) NOT NULL auto_increment, type varchar(255), rest number, level int(3))")) {
            pst.executeUpdate();
        }
        System.out.println("table Account created");
    }
}