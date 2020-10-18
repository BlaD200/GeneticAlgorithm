package ua.univ.vsynytsyn.timetable.domain.entities;

import lombok.Data;
import ua.univ.vsynytsyn.timetable.domain.data.LessonType;

import javax.persistence.*;

@SuppressWarnings("JpaDataSourceORMInspection")
@Data
@Entity
@Table(name = "study_block")
public class StudyBlock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studyBlockID;

    private Long lessonID;
    private Long lectorID;
    private Long groupID;
    private int studentsCount;
    private LessonType lessonType;
}
