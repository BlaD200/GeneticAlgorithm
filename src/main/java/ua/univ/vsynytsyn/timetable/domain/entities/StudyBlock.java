package ua.univ.vsynytsyn.timetable.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.univ.vsynytsyn.timetable.domain.data.LessonType;

import javax.persistence.*;

@SuppressWarnings("JpaDataSourceORMInspection")
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
