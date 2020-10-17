package ua.univ.vsynytsyn.timetable.domain.entities;

import lombok.Data;

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
}

