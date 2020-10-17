package ua.univ.vsynytsyn.timetable.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.univ.vsynytsyn.timetable.domain.entities.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
}
