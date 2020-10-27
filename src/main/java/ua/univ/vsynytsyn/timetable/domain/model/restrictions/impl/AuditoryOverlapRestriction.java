package ua.univ.vsynytsyn.timetable.domain.model.restrictions.impl;

import org.springframework.stereotype.Component;
import ua.univ.vsynytsyn.timetable.domain.model.Allele;

import java.util.Collections;
import java.util.List;

@Component
public class AuditoryOverlapRestriction extends OverlapRestriction {

    @Override
    List<Long> getEntityIds(Allele allele) {
        return Collections.singletonList(allele.getAuditoriumID());
    }

    @Override
    double getPenalty() {
        return 1;
    }
}
