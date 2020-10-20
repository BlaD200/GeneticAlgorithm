package ua.univ.vsynytsyn.timetable.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.univ.vsynytsyn.timetable.utils.CsvDeserializable;

import javax.persistence.*;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "auditorium")
public class Auditorium {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long auditoriumID;

    private String building;
    private String number;
    private int space;

    @CsvDeserializable
    public static Auditorium getInstance(String[] strings) {
        Auditorium auditorium = new Auditorium();

        auditorium.setAuditoriumID(Long.valueOf(strings[0]));
        auditorium.setBuilding(strings[1]);
        auditorium.setNumber(strings[2]);
        auditorium.setSpace(Integer.parseInt(strings[3]));

        return auditorium;
    }
}
