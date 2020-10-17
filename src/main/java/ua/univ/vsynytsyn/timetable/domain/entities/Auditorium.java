package ua.univ.vsynytsyn.timetable.domain.entities;

import lombok.Data;

import javax.persistence.*;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Data
@Table(name="auditorium")
public class Auditorium {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long auditoriumID;

    private String name;
    private String building;
    private String number;
    private int space;
}
