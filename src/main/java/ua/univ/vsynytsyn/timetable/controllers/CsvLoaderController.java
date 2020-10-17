package ua.univ.vsynytsyn.timetable.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ua.univ.vsynytsyn.timetable.domain.entities.*;
import ua.univ.vsynytsyn.timetable.exceptions.CsvDeserializableException;
import ua.univ.vsynytsyn.timetable.utils.CsvUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("/load/csv")
public class CsvLoaderController {

    @PostMapping(value = "/auditoriums")
    public List<Auditorium> auditoriums(@RequestParam("file") MultipartFile file)
            throws IOException, IllegalAccessException, CsvDeserializableException, InvocationTargetException {
        return load(file, Auditorium.class);
    }

    @PostMapping(value = "/groups")
    public List<Group> groups(@RequestParam("file") MultipartFile file)
            throws IOException, IllegalAccessException, CsvDeserializableException, InvocationTargetException {
        return load(file, Group.class);
    }

    @PostMapping(value = "/lectors")
    public List<Lector> lectors(@RequestParam("file") MultipartFile file)
            throws IOException, IllegalAccessException, CsvDeserializableException, InvocationTargetException {
        return load(file, Lector.class);
    }

    @PostMapping(value = "/lessons")
    public List<Lesson> lessons(@RequestParam("file") MultipartFile file)
            throws IOException, IllegalAccessException, CsvDeserializableException, InvocationTargetException {
        return load(file, Lesson.class);
    }

    @PostMapping(value = "/timeslots")
    public List<TimeSlot> timeslots(@RequestParam("file") MultipartFile file)
            throws IOException, IllegalAccessException, CsvDeserializableException, InvocationTargetException {
        return load(file, TimeSlot.class);
    }

    public <T> List<T> load(MultipartFile file, Class<T> clazz)
            throws IOException, IllegalAccessException, CsvDeserializableException, InvocationTargetException {
        String json = new String(file.getBytes());
        return CsvUtils.parseCsv(json, ", ", clazz);
    }
}
