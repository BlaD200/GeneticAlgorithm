package ua.univ.vsynytsyn.timetable.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.univ.vsynytsyn.timetable.domain.entities.StudyBlock;

@Repository
public interface StudyBlockRepository extends JpaRepository<StudyBlock, Long> {
}
