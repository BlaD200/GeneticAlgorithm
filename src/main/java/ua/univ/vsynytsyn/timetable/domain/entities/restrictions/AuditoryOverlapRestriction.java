package ua.univ.vsynytsyn.timetable.domain.entities.restrictions;

import ua.univ.vsynytsyn.timetable.domain.data.Allele;

public class AuditoryOverlapRestriction extends OverlapRestriction {

    public AuditoryOverlapRestriction(double penalty) {
        super(penalty);
    }

    @Override
    long getEntityId(Allele allele) {
        return allele.getAuditoriumID();
    }
}
