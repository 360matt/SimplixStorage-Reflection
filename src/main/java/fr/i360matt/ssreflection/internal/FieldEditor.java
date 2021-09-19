package fr.i360matt.ssreflection.internal;

import java.lang.reflect.Field;

public class FieldEditor {

    private final String path;
    private final Object instance;
    private final Field field;

    public FieldEditor (final String path, final Object instance, final Field field) {
        this.path = path;
        this.instance = instance;
        this.field = field;
    }

    public String getPath () {
        return path;
    }

    public Object getInstance () {
        return instance;
    }

    public Field getField () {
        return field;
    }
}
