package ua.univ.vsynytsyn.timetable.domain.model.restrictions.impl;

import org.springframework.stereotype.Component;
import ua.univ.vsynytsyn.timetable.domain.model.Allele;
import ua.univ.vsynytsyn.timetable.repositories.StudentRepository;

import java.util.List;

@Component
public class StudentsOverlapRestriction extends OverlapRestriction {

    private final StudentRepository studentRepository;


    public StudentsOverlapRestriction(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    @Override
    List<Long> getEntityIds(Allele allele) {
        return studentRepository.findAllStudentsByGroupIDEquals(allele.getStudyBlock().getGroupID());
    }


    @Override
    double getPenalty() {
        return 1;
    }
}
