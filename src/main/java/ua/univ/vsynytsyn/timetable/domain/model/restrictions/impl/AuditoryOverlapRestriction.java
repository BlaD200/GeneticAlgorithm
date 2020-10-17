package ua.univ.vsynytsyn.timetable.domain.model.restrictions.impl;

import ua.univ.vsynytsyn.timetable.domain.model.Allele;

public class AuditoryOverlapRestriction extends OverlapRestriction {

    public AuditoryOverlapRestriction(double penalty) {
        super(penalty);
    }

    @Override
    long getEntityId(Allele allele) {
        return allele.getAuditoriumID();
    }
}
