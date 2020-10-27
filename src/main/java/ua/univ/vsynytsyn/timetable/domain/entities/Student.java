package ua.univ.vsynytsyn.timetable.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "student")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    private Long studentID;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "students_groups",
            joinColumns = {@JoinColumn(name = "studentID")},
            inverseJoinColumns = {@JoinColumn(name = "groupID")}
    )
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private Set<Group> groups;
}
