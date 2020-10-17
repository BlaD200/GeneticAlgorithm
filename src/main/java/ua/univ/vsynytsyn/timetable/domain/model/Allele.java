package ua.univ.vsynytsyn.timetable.domain.model;

import lombok.Data;
import ua.univ.vsynytsyn.timetable.domain.data.StudyBlock;

@Data
public class Allele {
    private StudyBlock studyBlock;
    private Long auditoriumID;
    private Long timeSlotID;
}
