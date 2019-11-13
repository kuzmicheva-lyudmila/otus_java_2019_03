package ru.otus.homework;

import org.junit.jupiter.api.Test;
import ru.otus.homework.cache.CacheImpl;
import ru.otus.homework.dbservice.DBService;
import ru.otus.homework.dbservice.UserService;
import ru.otus.homework.models.AddressDataSet;
import ru.otus.homework.models.PhoneDataSet;
import ru.otus.homework.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CacheTest {

    @Test
    public void cacheForUser() {
        DBService userServiceWithoutCache = new UserService(new CacheImpl<String, User>());
        DBService userServiceWithCache = new UserService(null);
        int count = 100;

        List<Long> idListWithoutCache = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            User user = getUser("Jager" + i);
            userServiceWithoutCache.create(user);
            idListWithoutCache.add(user.getId());
        }
        System.out.println("=======================GET USER WITHOUT CACHE=============================");
        for (Long id: idListWithoutCache) {
            userServiceWithoutCache.load(id.longValue());
        }
        System.out.println("==========================================================================");

        List<Long> idListWithCache = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            User user = getUser("Michael" + i);
            userServiceWithCache.create(user);
            idListWithCache.add(user.getId());
        }
        System.out.println("=======================GET USER WITH CACHE=============================");
        for (Long id: idListWithCache) {
            userServiceWithCache.load(id.longValue());
        }
        System.out.println("=======================================================================");
    }

    private User getUser(String name) {
        User user = new User();
        user.setName(name);
        user.setAge(30);
        user.setAddress(new AddressDataSet("xxx", user));

        List<PhoneDataSet> phones = new ArrayList<>();
        phones.add(new PhoneDataSet("+78888888888", user));
        phones.add(new PhoneDataSet("+79999999999", user));
        user.setPhones(phones);
        return user;
    }
}
