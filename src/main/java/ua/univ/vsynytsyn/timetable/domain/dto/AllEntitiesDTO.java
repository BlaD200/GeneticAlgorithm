package ua.univ.vsynytsyn.timetable.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AllEntitiesDTO {
    private List<AuditoriumDTO> auditoriums;
    private List<GroupDTO> groups;
    private List<LectorDTO> lectors;
    private List<LessonDTO> lessons;
    private List<TimeSlotDTO> timeSlots;
    private List<StudyBlockDTO> studyBlocks;
}
