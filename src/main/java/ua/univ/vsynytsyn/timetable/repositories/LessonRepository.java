package ua.univ.vsynytsyn.timetable.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.univ.vsynytsyn.timetable.domain.entities.Lesson;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    Lesson findLessonByNameEquals(String name);
}
