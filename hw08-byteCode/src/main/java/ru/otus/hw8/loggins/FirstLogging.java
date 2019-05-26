package ru.otus.hw8.loggins;

import ru.otus.hw8.annotations.Log;

public interface FirstLogging {
    public void calculation(int param);

    public void calculationWithoutLog(int param1, String param2);

    public void calculationWithDiffParams(int param1, String param2);

    public void calculationWithDiffParams(int param1);
}
