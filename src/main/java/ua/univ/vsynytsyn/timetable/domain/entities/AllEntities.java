package ua.univ.vsynytsyn.timetable.domain.entities;

import lombok.Data;

import java.util.List;

@Data
public class AllEntities {
    private List<Auditorium> auditoriums;
    private List<Group> groups;
    private List<Lector> lectors;
    private List<Lesson> lessons;
    private List<TimeSlot> timeSlots;
}
