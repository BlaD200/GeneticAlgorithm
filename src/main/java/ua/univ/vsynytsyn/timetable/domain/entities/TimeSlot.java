package ua.univ.vsynytsyn.timetable.domain.entities;

import lombok.Data;
import ua.univ.vsynytsyn.timetable.utils.CsvDeserializable;

import javax.persistence.*;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Data
@Table(name = "time_slot")
public class TimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long timeslotID;

    private String name;

    @CsvDeserializable
    public static TimeSlot getInstance(String[] strings) {
        TimeSlot timeSlot = new TimeSlot();

        timeSlot.setTimeslotID(Long.valueOf(strings[0]));
        timeSlot.setName(strings[1]);

        return timeSlot;
    }
}

