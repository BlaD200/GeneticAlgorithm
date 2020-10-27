package ua.univ.vsynytsyn.timetable.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.univ.vsynytsyn.timetable.domain.data.LessonType;
import ua.univ.vsynytsyn.timetable.domain.dto.*;
import ua.univ.vsynytsyn.timetable.domain.entities.*;
import ua.univ.vsynytsyn.timetable.repositories.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LoadService {

    private final AuditoriumRepository auditoriumRepository;
    private final GroupsRepository groupsRepository;
    private final LectorRepository lectorRepository;
    private final LessonRepository lessonRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final StudyBlockRepository studyBlockRepository;
    private final StudentRepository studentRepository;


    @Autowired
    public LoadService(AuditoriumRepository auditoriumRepository,
                       GroupsRepository groupsRepository,
                       LectorRepository lectorRepository,
                       LessonRepository lessonRepository,
                       TimeSlotRepository timeSlotRepository,
                       StudyBlockRepository studyBlockRepository, StudentRepository studentRepository) {
        this.auditoriumRepository = auditoriumRepository;
        this.groupsRepository = groupsRepository;
        this.lectorRepository = lectorRepository;
        this.lessonRepository = lessonRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.studyBlockRepository = studyBlockRepository;
        this.studentRepository = studentRepository;
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

        int maxStudents = studyBlocks.stream().max(Comparator.comparing(StudyBlock::getStudentsCount)).get().getStudentsCount();
        List<Student> students = new ArrayList<>(maxStudents);
        for (int i = 0; i < maxStudents; i++) {
            students.add(i, Student.builder().studentID((long) i).groups(new HashSet<>()).build());
        }

        Map<Long, List<StudyBlock>> map = studyBlocks
                .stream()
                .collect(Collectors.groupingBy(StudyBlock::getLessonID));

        map.forEach((lessonId, blocks) -> {
            int studsInGroup = 0;
            for (StudyBlock block : blocks) {
                if (block.getLessonType() != LessonType.PRACTICE) {
                    Optional<Group> groupOptional = groupsRepository.findById(block.getGroupID());
                    if (groupOptional.isPresent()) {
                        for (int j = 0; j < block.getStudentsCount(); j++) {
                            students.get(j).getGroups().add(groupOptional.get());
                        }
                    }
                    blocks.remove(block);
                    break;
                }
            }

            for (StudyBlock block : blocks) {
                if (block.getLessonType() == LessonType.PRACTICE)
                    studsInGroup = block.getStudentsCount();
            }

            for (int i = 0; i < blocks.size(); i++) {
                Optional<Group> groupOptional = groupsRepository.findById(blocks.get(i).getGroupID());
                if (groupOptional.isPresent())
                    if (blocks.get(i).getLessonType() == LessonType.PRACTICE) {
                        for (int k = studsInGroup * i; k < studsInGroup * (i + 1); k += 1) {
                            students.get(k).getGroups().add(groupOptional.get());
                        }
                    }
            }
        });

        studentRepository.saveAll(students);
        studyBlockRepository.saveAll(studyBlocks);


        groupsRepository.findAll();
    }


    public void deleteAll() {
        auditoriumRepository.deleteAll();
        groupsRepository.deleteAll();
        lectorRepository.deleteAll();
        lessonRepository.deleteAll();
        studyBlockRepository.deleteAll();
        timeSlotRepository.deleteAll();
    }
}
