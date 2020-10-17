package ua.univ.vsynytsyn.timetable.domain.entities.restrictions;

import ua.univ.vsynytsyn.timetable.domain.data.Allele;

public class LectorOverlapRestriction extends OverlapRestriction {

    public LectorOverlapRestriction(double penalty) {
        super(penalty);
    }

    @Override
    long getEntityId(Allele allele) {
        return allele.getStudyBlock().getLectorID();
    }
}
