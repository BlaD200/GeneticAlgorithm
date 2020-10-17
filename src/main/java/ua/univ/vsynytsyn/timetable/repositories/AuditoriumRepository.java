package ua.univ.vsynytsyn.timetable.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.univ.vsynytsyn.timetable.domain.entities.Auditorium;

import java.util.List;

public interface AuditoriumRepository extends JpaRepository<Auditorium, Long> {

    List<Auditorium> findAuditoriumsBySpaceGreaterThanEqual(int capacity);
}
