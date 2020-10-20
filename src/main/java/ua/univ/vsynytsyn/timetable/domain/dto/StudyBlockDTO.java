package ua.univ.vsynytsyn.timetable.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.univ.vsynytsyn.timetable.domain.data.LessonType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyBlockDTO {
    private String lessonName;
    private String lectorName;
    private String groupName;
    private int studentsCount;
    private LessonType lessonType;
}
