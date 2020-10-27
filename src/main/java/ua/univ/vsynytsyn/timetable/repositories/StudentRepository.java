package ua.univ.vsynytsyn.timetable.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.univ.vsynytsyn.timetable.domain.entities.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query(value = "Select student.studentid from student " +
            "left join students_groups sg on sg.groupid = :id " +
            "where sg.studentid = student.studentid", nativeQuery = true)
    List<Long> findAllStudentsByGroupIDEquals(@Param("id") Long groupId);
}
