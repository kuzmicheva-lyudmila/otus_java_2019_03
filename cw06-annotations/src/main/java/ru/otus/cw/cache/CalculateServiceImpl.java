package ru.otus.cw.cache;

public class CalculateServiceImpl implements CalculateService {

    @Cache
    public int add(Operation operation) {
        return operation.getA() + operation.getB();
    }
}
