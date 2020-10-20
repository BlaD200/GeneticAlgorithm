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
@Table(name = "time_slot")
public class TimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long timeslotID;

    private String day;
    private String time;

    @CsvDeserializable
    public static TimeSlot getInstance(String[] strings) {
        TimeSlot timeSlot = new TimeSlot();

        timeSlot.setTimeslotID(Long.valueOf(strings[0]));
        timeSlot.setDay(strings[1]);
        timeSlot.setTime(strings[2]);

        return timeSlot;
    }
}

