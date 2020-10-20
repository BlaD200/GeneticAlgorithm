package ua.univ.vsynytsyn.timetable.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.univ.vsynytsyn.timetable.domain.entities.Group;

@Repository
public interface GroupsRepository extends JpaRepository<Group, Long> {
    Group findGroupByNameEquals(String name);
}
