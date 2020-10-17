package ua.univ.vsynytsyn.timetable.domain.entities;

import lombok.Data;
import ua.univ.vsynytsyn.timetable.utils.CsvDeserializable;

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

    @CsvDeserializable
    public static Lector getInstance(String[] strings) {
        Lector lector = new Lector();

        lector.setLectorID(Long.valueOf(strings[0]));
        lector.setName(strings[1]);

        return lector;
    }
}
