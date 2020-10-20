package ua.univ.vsynytsyn.timetable.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.univ.vsynytsyn.timetable.domain.dto.*;
import ua.univ.vsynytsyn.timetable.domain.entities.*;
import ua.univ.vsynytsyn.timetable.repositories.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoadService {

    private final AuditoriumRepository auditoriumRepository;
    private final GroupsRepository groupsRepository;
    private final LectorRepository lectorRepository;
    private final LessonRepository lessonRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final StudyBlockRepository studyBlockRepository;


    @Autowired
    public LoadService(AuditoriumRepository auditoriumRepository,
                       GroupsRepository groupsRepository,
                       LectorRepository lectorRepository,
                       LessonRepository lessonRepository,
                       TimeSlotRepository timeSlotRepository,
                       StudyBlockRepository studyBlockRepository) {
        this.auditoriumRepository = auditoriumRepository;
        this.groupsRepository = groupsRepository;
        this.lectorRepository = lectorRepository;
        this.lessonRepository = lessonRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.studyBlockRepository = studyBlockRepository;
    }


    public AllEntities getAll() {
        return AllEntities.builder()
                .auditoriums(auditoriumRepository.findAll())
                .groups(groupsRepository.findAll())
                .lectors(lectorRepository.findAll())
                .lessons(lessonRepository.findAll())
                .studyBlocks(studyBlockRepository.findAll())
                .timeSlots(timeSlotRepository.findAll())
                .build();
    }


    public void saveAll(AllEntitiesDTO all) {
        saveAuditoriums(all.getAuditoriums());
        saveGroups(all.getGroups());
        saveLectors(all.getLectors());
        saveLessons(all.getLessons());
        saveTimeSlots(all.getTimeSlots());
        saveStudyBlocks(all.getStudyBlocks());
    }


    public void saveAuditoriums(List<AuditoriumDTO> auditoriumsDTOs) {
        List<Auditorium> auditoriums = auditoriumsDTOs.stream().map(it -> Auditorium
                .builder()
                .building(it.getBuilding())
                .number(it.getNumber())
                .space(it.getSpace())
                .build()
        ).collect(Collectors.toList());
        auditoriumRepository.saveAll(auditoriums);
    }


    public void saveGroups(List<GroupDTO> groupsDTOs) {
        List<Group> groups = groupsDTOs.stream().map(it -> Group
                .builder()
                .name(it.getName())
                .build()
        ).collect(Collectors.toList());
        groupsRepository.saveAll(groups);
    }


    public void saveLectors(List<LectorDTO> lectorDTOs) {
        List<Lector> lectors = lectorDTOs.stream().map(it -> Lector
                .builder()
                .name(it.getName())
                .build()
        ).collect(Collectors.toList());
        lectorRepository.saveAll(lectors);
    }


    public void saveLessons(List<LessonDTO> lessonsDTOs) {
        List<Lesson> lessons = lessonsDTOs.stream().map(it -> Lesson
                .builder()
                .name(it.getName())
                .build()
        ).collect(Collectors.toList());
        lessonRepository.saveAll(lessons);
    }


    public void saveTimeSlots(List<TimeSlotDTO> timeSlotsDTOs) {
        List<TimeSlot> timeSlots = timeSlotsDTOs.stream().map(it -> TimeSlot
                .builder()
                .day(it.getDay())
                .time(it.getTime())
                .build()
        ).collect(Collectors.toList());
        timeSlotRepository.saveAll(timeSlots);
    }


    public void saveStudyBlocks(List<StudyBlockDTO> studyBlocksDTOs) {
        List<StudyBlock> studyBlocks = studyBlocksDTOs.stream().map(it -> StudyBlock
                .builder()
                .groupID(groupsRepository.findGroupByNameEquals(it.getGroupName()).getGroupID())
                .lectorID(lectorRepository.findLectorByNameEquals(it.getLectorName()).getLectorID())
                .lessonID(lessonRepository.findLessonByNameEquals(it.getLessonName()).getLessonID())
                .lessonType(it.getLessonType())
                .studentsCount(it.getStudentsCount())
                .build()
        ).collect(Collectors.toList());
        studyBlockRepository.saveAll(studyBlocks);
    }


    public void deleteAll(){
        auditoriumRepository.deleteAll();
        groupsRepository.deleteAll();
        lectorRepository.deleteAll();
        lessonRepository.deleteAll();
        studyBlockRepository.deleteAll();
        timeSlotRepository.deleteAll();
    }
}
