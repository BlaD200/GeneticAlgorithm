package ua.univ.vsynytsyn.timetable.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.univ.vsynytsyn.timetable.domain.entities.Lector;

public interface LectorRepository extends JpaRepository<Lector, Long> {
}
