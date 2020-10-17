package ua.univ.vsynytsyn.timetable.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.univ.vsynytsyn.timetable.domain.entities.*;
import ua.univ.vsynytsyn.timetable.repositories.*;
import ua.univ.vsynytsyn.timetable.utils.JsonUtils;

import java.util.List;

@Service
public class LoadService {

    private final AuditoriumRepository auditoriumRepository;
    private final GroupsRepository groupsRepository;
    private final LectorRepository lectorRepository;
    private final LessonRepository lessonRepository;
    private final TimeSlotRepository timeSlotRepository;

    @Autowired
    public LoadService(AuditoriumRepository auditoriumRepository,
                       GroupsRepository groupsRepository,
                       LectorRepository lectorRepository,
                       LessonRepository lessonRepository,
                       TimeSlotRepository timeSlotRepository) {
        this.auditoriumRepository = auditoriumRepository;
        this.groupsRepository = groupsRepository;
        this.lectorRepository = lectorRepository;
        this.lessonRepository = lessonRepository;
        this.timeSlotRepository = timeSlotRepository;
    }

    public void saveAll(AllEntities all) {
        saveAuditoriums(all.getAuditoriums());
        saveGroups(all.getGroups());
        saveLectors(all.getLectors());
        saveLessons(all.getLessons());
        saveTimeSlots(all.getTimeSlots());
    }

    public void saveAuditoriums(List<Auditorium> auditoriums) {
        auditoriumRepository.saveAll(auditoriums);
    }

    public void saveGroups(List<Group> groups) {
        groupsRepository.saveAll(groups);
    }

    public void saveLectors(List<Lector> lectors) {
        lectorRepository.saveAll(lectors);
    }

    public void saveLessons(List<Lesson> lessons) {
        lessonRepository.saveAll(lessons);
    }

    public void saveTimeSlots(List<TimeSlot> timeSlots) {
        timeSlotRepository.saveAll(timeSlots);
    }
}
