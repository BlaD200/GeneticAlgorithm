package ua.univ.vsynytsyn.timetable.utils;

import ua.univ.vsynytsyn.timetable.exceptions.CsvDeserializableException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

public class CsvUtils {

    public static <T> List<T> parseCsv(String csv, String separator, Class<T> clazz)
            throws IllegalAccessException, InvocationTargetException, CsvDeserializableException {
        List<T> result = new LinkedList<>();

        Method csvCtor = null;

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(CsvDeserializable.class)) {
                method.setAccessible(true);
                csvCtor = method;
                break;
            }
        }

        if (csvCtor == null) {
            throw new CsvDeserializableException("The class " + clazz.getSimpleName() +
                    " does not have method annotated with " + CsvDeserializable.class.getSimpleName());
        }

        String[] lines = csv.split("\\r?\\n");

        for (String line : lines) {
            String[] args = line.split(separator);
            result.add((T) csvCtor.invoke(null, (Object) args));
        }

        return result;
    }
}
