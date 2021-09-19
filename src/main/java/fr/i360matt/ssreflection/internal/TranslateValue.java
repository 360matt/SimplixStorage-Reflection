package fr.i360matt.ssreflection.internal;

import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

public class TranslateValue {

    public static Object load (Object brut, Class<?> type, String path, Gson gson) {
        try {
            if (type == int.class)
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
            else if (type == Map.class || type == List.class)
                return brut;
            return gson.fromJson(brut.toString(), type);
        } catch (NumberFormatException e) {

            throw new RuntimeException("Field '" + path + "' require value type [" + type.getName() + "], but given '" + brut + "'");
        }




    }

    public static Object save (Object brut, Class<?> type, Gson gson) {
        boolean cond = type.isPrimitive();
        cond = cond || type == String.class;
        cond = cond || type == String[].class;
        cond = cond || type == Map.class;
        cond = cond || type == List.class;

        if (cond)
            return brut;
        return gson.toJson(brut.toString());
    }

}
