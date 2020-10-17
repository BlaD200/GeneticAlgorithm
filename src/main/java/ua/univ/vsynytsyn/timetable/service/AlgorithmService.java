package ua.univ.vsynytsyn.timetable.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.univ.vsynytsyn.timetable.domain.data.StudyBlock;
import ua.univ.vsynytsyn.timetable.repositories.GroupsRepository;
import ua.univ.vsynytsyn.timetable.repositories.LectorRepository;
import ua.univ.vsynytsyn.timetable.repositories.LessonRepository;

import java.util.List;

@Service
public class AlgorithmService {

    private final GroupsRepository groupsRepository;
    private final LessonRepository lessonRepository;
    private final LectorRepository lectorRepository;


    @Autowired
    public AlgorithmService(GroupsRepository groupsRepository, LessonRepository lessonRepository, LectorRepository lectorRepository) {
        this.groupsRepository = groupsRepository;
        this.lessonRepository = lessonRepository;
        this.lectorRepository = lectorRepository;
    }


    public List<StudyBlock> createStudyBlocks(){
        return null;
    }
}
