package ua.univ.vsynytsyn.timetable.domain.entities;

import lombok.Data;
import ua.univ.vsynytsyn.timetable.utils.CsvDeserializable;

import javax.persistence.*;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Data
@Table(name = "lesson")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lessonID;

    private String name;

    @CsvDeserializable
    public static Lesson getInstance(String[] strings) {
        Lesson lesson = new Lesson();

        lesson.setLessonID(Long.valueOf(strings[0]));
        lesson.setName(strings[1]);

        return lesson;
    }
}
