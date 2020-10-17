package ua.univ.vsynytsyn.timetable.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ua.univ.vsynytsyn.timetable.domain.entities.*;
import ua.univ.vsynytsyn.timetable.exceptions.CsvDeserializableException;
import ua.univ.vsynytsyn.timetable.service.LoadService;
import ua.univ.vsynytsyn.timetable.utils.CsvUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("/load/csv")
public class CsvLoaderController {

    private final LoadService loadService;

    @Autowired
    public CsvLoaderController(LoadService loadService) {
        this.loadService = loadService;
    }

    @PostMapping(value = "/auditoriums")
    public List<Auditorium> auditoriums(@RequestParam("file") MultipartFile file)
            throws IOException, IllegalAccessException, CsvDeserializableException, InvocationTargetException {
        List<Auditorium> auditoriums = load(file, Auditorium.class);
        loadService.saveAuditoriums(auditoriums);
        return auditoriums;
    }

    @PostMapping(value = "/groups")
    public List<Group> groups(@RequestParam("file") MultipartFile file)
            throws IOException, IllegalAccessException, CsvDeserializableException, InvocationTargetException {
        List<Group> groups = load(file, Group.class);
        loadService.saveGroups(groups);
        return groups;
    }

    @PostMapping(value = "/lectors")
    public List<Lector> lectors(@RequestParam("file") MultipartFile file)
            throws IOException, IllegalAccessException, CsvDeserializableException, InvocationTargetException {
        List<Lector> lectors = load(file, Lector.class);
        loadService.saveLectors(lectors);
        return lectors;
    }

    @PostMapping(value = "/lessons")
    public List<Lesson> lessons(@RequestParam("file") MultipartFile file)
            throws IOException, IllegalAccessException, CsvDeserializableException, InvocationTargetException {
        List<Lesson> lessons = load(file, Lesson.class);
        loadService.saveLessons(lessons);
        return lessons;
    }

    @PostMapping(value = "/timeslots")
    public List<TimeSlot> timeslots(@RequestParam("file") MultipartFile file)
            throws IOException, IllegalAccessException, CsvDeserializableException, InvocationTargetException {
        List<TimeSlot> timeSlots = load(file, TimeSlot.class);
        loadService.saveTimeSlots(timeSlots);
        return timeSlots;
    }

    public <T> List<T> load(MultipartFile file, Class<T> clazz)
            throws IOException, IllegalAccessException, CsvDeserializableException, InvocationTargetException {
        String json = new String(file.getBytes());
        return CsvUtils.parseCsv(json, ", ", clazz);
    }
}
