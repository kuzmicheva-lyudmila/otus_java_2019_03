package ru.otus.cw.cache;

public interface CalculateService {

    @Cache
    int add(Operation operation);
}
