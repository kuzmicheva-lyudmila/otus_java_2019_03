package ru.otus.cw.cache;

import lombok.Builder;
import lombok.Data;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Data
@Builder
public class Operation {

    private final int a;
    private final int b;

//    public Operation(int a, int b) {
//        this.a = a;
//        this.b = b;
//    }
//
//    public int getA() {
//        return a;
//    }
//
//    public int getB() {
//        return b;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Operation operation = (Operation) o;
//        return a == operation.a &&
//                b == operation.b;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(a, b);
//    }
}

