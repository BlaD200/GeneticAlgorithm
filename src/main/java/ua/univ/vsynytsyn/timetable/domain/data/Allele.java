package ua.univ.vsynytsyn.timetable.domain.data;

import lombok.Data;

@Data
public class Allele {
    private StudyBlock studyBlock;
    private Long auditoriumID;
    private Long timeSlotID;
}
