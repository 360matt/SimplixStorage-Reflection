package fr.i360matt.ssreflection.internal;

import com.google.gson.Gson;
import de.leonhard.storage.internal.FlatFile;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class Reflect {

    protected final File file;
    protected FlatFile config;
    protected Gson gson = new Gson();

    public Reflect (final File file) {
        this.file = file;
    }

    public void load () {
        try {
            loadRecursive((fieldEditor) -> {

                final String path = fieldEditor.getPath();

                final Object value;

                final Object brut = this.config.get(path);
                if (brut != null) {
                    value = TranslateValue.load(brut, fieldEditor.getField().getType(), path, gson);
                    if (value == null)
                        return;

                    try {
                        fieldEditor.getField().set(fieldEditor.getInstance(), value);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void save () {
        try {
            loadRecursive((fieldEditor) -> {
                try {
                    final Object brut = fieldEditor.getField().get(fieldEditor.getInstance());
                    final Object value = TranslateValue.save(brut, fieldEditor.getField().getType(), gson);
                    this.config.getFileData().insert(fieldEditor.getPath(), value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
            this.config.write();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void loadRecursive (Consumer<FieldEditor> consumer) throws IllegalAccessException {
        this.loadRecursive("", this, consumer);
    }

    private void loadRecursive (String prefix, Object instance, Consumer<FieldEditor> consumer) throws IllegalAccessException {
        for (final Field field : instance.getClass().getDeclaredFields()) {

            boolean cond = field.getType().isPrimitive();
            cond = cond || field.getType() == String.class;
            cond = cond || field.getType() == String[].class;
            cond = cond || field.getType() == Map.class;
            cond = cond || field.getType() == List.class;
            cond = cond || Serializable.class.isAssignableFrom(field.getType());

            if (cond) {
                FieldEditor fieldEditor = new FieldEditor(prefix + field.getName(), instance, field);
                consumer.accept(fieldEditor);
                continue;
            }

            final Object nextInstance = field.get(instance);
            if (nextInstance != null) {
                this.loadRecursive(prefix + field.getName() + ".", nextInstance, consumer);
            }
        }
    }

    public File getFile () {
        return file;
    }
}
