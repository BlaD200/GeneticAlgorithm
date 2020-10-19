package ua.univ.vsynytsyn.timetable.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AllEntities {
    private List<Auditorium> auditoriums;
    private List<Group> groups;
    private List<Lector> lectors;
    private List<Lesson> lessons;
    private List<TimeSlot> timeSlots;
    private List<StudyBlock> studyBlocks;
}
