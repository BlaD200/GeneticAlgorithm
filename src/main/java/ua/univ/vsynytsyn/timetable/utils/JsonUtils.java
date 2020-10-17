package ua.univ.vsynytsyn.timetable.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ua.univ.vsynytsyn.timetable.domain.entities.AllEntities;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JsonUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> List<T> parseJsonArray(String json, Class<T> classOnWhichArrayIsDefined)
            throws IOException, ClassNotFoundException {
        Class<T[]> arrayClass = (Class<T[]>) Class.forName("[L" + classOnWhichArrayIsDefined.getName() + ";");
        T[] objects = mapper.readValue(json, arrayClass);
        return Arrays.asList(objects);
    }

    public static AllEntities parseJsonAll(String json) throws JsonProcessingException {
        return mapper.readValue(json, AllEntities.class);
    }
}
