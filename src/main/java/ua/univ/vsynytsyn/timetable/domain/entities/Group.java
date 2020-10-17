package ua.univ.vsynytsyn.timetable.domain.entities;

import lombok.Data;

import javax.persistence.*;

@SuppressWarnings("JpaDataSourceORMInspection")
@Data
@Entity
@Table(name = "grp")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupID;

    private String name;
}
