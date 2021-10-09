package fr.i360matt.ssreflection.internal;

import com.google.gson.Gson;
import fr.i360matt.ssreflection.serialize.GenericList;
import fr.i360matt.ssreflection.serialize.GenericMap;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class TranslateValue {

    private final Reflect reflect;
    private final Gson gson;

    public TranslateValue (final Reflect reflect, final Gson gson) {
        this.reflect = reflect;
        this.gson = gson;
    }


    protected Object load (Object brut, FieldEditor fieldEditor, String path) {
        Field field = fieldEditor.getField();
        Class<?> type = field.getType();
        try {
            if (brut == null)
                return null;
            else if (type == int.class)
                return Integer.parseInt(brut.toString());
            else if (type == long.class)
                return Long.parseLong(brut.toString());
            else if (type == double.class)
                return Double.parseDouble(brut.toString());
            else if (type == float.class)
                return Float.parseFloat(brut.toString());
            else if (type == char.class)
                return (char) Integer.parseInt(brut.toString());
            else if (type == boolean.class)
                return Boolean.parseBoolean(brut.toString());
            else if (GenericList.class.isAssignableFrom(type)) {
                GenericList<?> gen = (GenericList<?>) fieldEditor.getField().get(fieldEditor.getInstance());
                gen.loadAll((List<Map<String, Object>>) reflect.getFlatFile().getList(path));
                return null;
            }
            else if (GenericMap.class.isAssignableFrom(type)) {
                GenericMap<?, ?> gen = (GenericMap<?, ?>) fieldEditor.getField().get(fieldEditor.getInstance());
                gen.loadAll((Map<String, Object>) reflect.getFlatFile().getMap(path));
                return null;
            }
            else if (type == Map.class || type == List.class)
                return brut;
            return this.gson.fromJson(brut.toString(), type);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Field '" + path + "' require value type [" + type.getName() + "], but given '" + brut + "'");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object save (Object brut, FieldEditor fieldEditor) {
        Field field = fieldEditor.getField();
        Class<?> type = field.getType();

        boolean cond;
        cond = type.isPrimitive();
        cond = cond || type == String.class;
        cond = cond || type == String[].class;
        cond = cond || type == Map.class;
        cond = cond || type == List.class;

        if (cond)
            return brut;
        else if (GenericList.class.isAssignableFrom(type)) {
            try {
                GenericList<?> gen = (GenericList<?>) fieldEditor.getField().get(fieldEditor.getInstance());
                return gen.saveAll();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else if (GenericMap.class.isAssignableFrom(type)) {
            try {
                GenericMap<?,?> gen = (GenericMap<?,?>) fieldEditor.getField().get(fieldEditor.getInstance());
                return gen.saveAll();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return this.gson.toJson(brut.toString());
    }

}
