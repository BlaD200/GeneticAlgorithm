package ua.univ.vsynytsyn.timetable.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditoriumDTO {

    private String building;
    private String number;
    private int space;
}
