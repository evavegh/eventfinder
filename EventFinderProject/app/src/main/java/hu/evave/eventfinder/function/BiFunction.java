package hu.evave.eventfinder.function;

public interface BiFunction<A, B, T> {
    T apply(A a, B b);
}
