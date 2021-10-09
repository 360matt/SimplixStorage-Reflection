package fr.i360matt.ssreflection.utils;

public interface ThrowableConsumer<T> {
    void accept (final T type) throws Throwable;
}
