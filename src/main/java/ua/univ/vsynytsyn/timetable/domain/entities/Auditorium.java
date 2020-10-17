package ua.univ.vsynytsyn.timetable.domain.entities;

import lombok.Data;
import ua.univ.vsynytsyn.timetable.utils.CsvDeserializable;

import javax.persistence.*;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Data
@Table(name = "auditorium")
public class Auditorium {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long auditoriumID;

    private String name;
    private String building;
    private String number;
    private int space;

    @CsvDeserializable
    public static Auditorium getInstance(String[] strings) {
        Auditorium auditorium = new Auditorium();

        auditorium.setAuditoriumID(Long.valueOf(strings[0]));
        auditorium.setName(strings[1]);
        auditorium.setBuilding(strings[2]);
        auditorium.setNumber(strings[3]);
        auditorium.setSpace(Integer.parseInt(strings[4]));

        return auditorium;
    }
}
