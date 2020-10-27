package ua.univ.vsynytsyn.timetable.domain.model.restrictions.impl;

import org.springframework.stereotype.Component;
import ua.univ.vsynytsyn.timetable.domain.model.Allele;

import java.util.Collections;
import java.util.List;

@Component
public class LectorOverlapRestriction extends OverlapRestriction {

    @Override
    List<Long> getEntityIds(Allele allele) {
        return Collections.singletonList(allele.getStudyBlock().getLectorID());
    }

    @Override
    double getPenalty() {
        return 1;
    }
}
