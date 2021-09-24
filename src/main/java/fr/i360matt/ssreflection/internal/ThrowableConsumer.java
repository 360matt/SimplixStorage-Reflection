package fr.i360matt.ssreflection.internal;

public interface ThrowableConsumer<T> {
    void accept (final T type) throws Throwable;
}
