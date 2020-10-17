package ua.univ.vsynytsyn.timetable.domain.model.restrictions.impl;

import ua.univ.vsynytsyn.timetable.domain.model.Allele;

public class LectorOverlapRestriction extends OverlapRestriction {

    @Override
    long getEntityId(Allele allele) {
        return allele.getStudyBlock().getLectorID();
    }

    @Override
    double getPenalty() {
        return -1;
    }
}
