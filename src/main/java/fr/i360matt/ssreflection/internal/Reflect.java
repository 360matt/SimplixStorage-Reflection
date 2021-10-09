package fr.i360matt.ssreflection.internal;

import com.google.gson.Gson;
import de.leonhard.storage.internal.FlatFile;
import fr.i360matt.ssreflection.serialize.GenericList;
import fr.i360matt.ssreflection.serialize.GenericMap;
import fr.i360matt.ssreflection.utils.ThrowableConsumer;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class Reflect {

    protected final File file;
    protected FlatFile config;
    protected final Gson gson;
    protected final TranslateValue translateValue;

    public Reflect (final File file) {
        this.file = file;

        this.gson = new Gson();
        this.translateValue = new TranslateValue(this, this.gson);
    }

    public void load () {
        try {
            doRecursively((fieldEditor) -> {
                final String path = fieldEditor.getPath();

                final Object value = this.translateValue.load(this.config.get(path), fieldEditor, path);
                if (value == null)
                    return;
                fieldEditor.getField().set(fieldEditor.getInstance(), value);
            });
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void save () {
        try {
            doRecursively((fieldEditor) -> {

                final Object brut = fieldEditor.getField().get(fieldEditor.getInstance());
                final Object value = this.translateValue.save(brut, fieldEditor);
                this.config.getFileData().insert(fieldEditor.getPath(), value);
            });
            this.config.write();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void doRecursively (ThrowableConsumer<FieldEditor> consumer) throws Throwable {
        this.doRecursively("", this, consumer);
    }

    public void doRecursively (String prefix, Object instance, ThrowableConsumer<FieldEditor> consumer) throws Throwable {
        for (final Field field : instance.getClass().getDeclaredFields()) {

            boolean cond = field.getType().isPrimitive();
            cond = cond || field.getType() == String.class;
            cond = cond || field.getType() == String[].class;
            cond = cond || field.getType() == Map.class;
            cond = cond || field.getType() == List.class;
            cond = cond || GenericList.class.isAssignableFrom(field.getType());
            cond = cond || GenericMap.class.isAssignableFrom(field.getType());
            // cond = cond || Serializable.class.isAssignableFrom(field.getType());

            if (cond) {
                FieldEditor fieldEditor = new FieldEditor(prefix + field.getName(), instance, field);
                consumer.accept(fieldEditor);
                continue;
            }

            final Object nextInstance = field.get(instance);
            if (nextInstance != null) {
                this.doRecursively(prefix + field.getName() + ".", nextInstance, consumer);
            }
        }
    }

    public File getFile () {
        return file;
    }

    public Gson getGson () {
        return gson;
    }

    protected FlatFile getFlatFile () {
        return config;
    }
}
