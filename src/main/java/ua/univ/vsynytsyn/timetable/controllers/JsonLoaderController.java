package ua.univ.vsynytsyn.timetable.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ua.univ.vsynytsyn.timetable.domain.entities.*;
import ua.univ.vsynytsyn.timetable.service.LoadService;
import ua.univ.vsynytsyn.timetable.utils.JsonUtils;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/load/json")
public class JsonLoaderController {

    private final LoadService loadService;

    @Autowired
    public JsonLoaderController(LoadService loadService) {
        this.loadService = loadService;
    }

    @PostMapping(value = "/all")
    public AllEntities all(@RequestParam("file") MultipartFile file) throws IOException {
        AllEntities all = loadAll(file);
        loadService.saveAll(all);
        return all;
    }

    @PostMapping(value = "/auditoriums")
    public List<Auditorium> auditoriums(@RequestParam("file") MultipartFile file) throws IOException, ClassNotFoundException {
        List<Auditorium> auditoriums = load(file, Auditorium.class);
        loadService.saveAuditoriums(auditoriums);
        return auditoriums;
    }

    @PostMapping(value = "/groups")
    public List<Group> groups(@RequestParam("file") MultipartFile file) throws IOException, ClassNotFoundException {
        List<Group> groups = load(file, Group.class);
        loadService.saveGroups(groups);
        return groups;
    }

    @PostMapping(value = "/lectors")
    public List<Lector> lectors(@RequestParam("file") MultipartFile file) throws IOException, ClassNotFoundException {
        List<Lector> lectors = load(file, Lector.class);
        loadService.saveLectors(lectors);
        return lectors;
    }

    @PostMapping(value = "/lessons")
    public List<Lesson> lessons(@RequestParam("file") MultipartFile file) throws IOException, ClassNotFoundException {
        List<Lesson> lessons = load(file, Lesson.class);
        loadService.saveLessons(lessons);
        return lessons;
    }

    @PostMapping(value = "/timeslots")
    public List<TimeSlot> timeslots(@RequestParam("file") MultipartFile file) throws IOException, ClassNotFoundException {
        List<TimeSlot> timeSlots = load(file, TimeSlot.class);
        loadService.saveTimeSlots(timeSlots);
        return timeSlots;
    }

    public AllEntities loadAll(MultipartFile file) throws IOException {
        String json = new String(file.getBytes());
        return JsonUtils.parseJsonAll(json);
    }

    public <T> List<T> load(MultipartFile file, Class<T> clazz) throws IOException, ClassNotFoundException {
        String json = new String(file.getBytes());
        return JsonUtils.parseJsonArray(json, clazz);
    }
}
