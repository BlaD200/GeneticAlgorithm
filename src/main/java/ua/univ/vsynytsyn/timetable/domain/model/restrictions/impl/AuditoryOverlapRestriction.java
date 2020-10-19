package ua.univ.vsynytsyn.timetable.domain.model.restrictions.impl;

import org.springframework.stereotype.Component;
import ua.univ.vsynytsyn.timetable.domain.model.Allele;

@Component
public class AuditoryOverlapRestriction extends OverlapRestriction {

    @Override
    long getEntityId(Allele allele) {
        return allele.getAuditoriumID();
    }

    @Override
    double getPenalty() {
        return 1;
    }
}
