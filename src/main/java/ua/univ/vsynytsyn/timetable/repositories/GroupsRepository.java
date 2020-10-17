package ua.univ.vsynytsyn.timetable.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.univ.vsynytsyn.timetable.domain.entities.Group;

public interface GroupsRepository extends JpaRepository<Group, Long> {
}
