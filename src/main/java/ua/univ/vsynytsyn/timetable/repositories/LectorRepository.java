package ua.univ.vsynytsyn.timetable.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.univ.vsynytsyn.timetable.domain.entities.Lector;

@Repository
public interface LectorRepository extends JpaRepository<Lector, Long> {
    Lector findLectorByNameEquals(String name);
}
