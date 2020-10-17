package ua.univ.vsynytsyn.timetable.domain.entities;

import lombok.Data;

import javax.persistence.*;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Data
@Table(name = "lector")
public class Lector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lectorID;

    private String name;
}
