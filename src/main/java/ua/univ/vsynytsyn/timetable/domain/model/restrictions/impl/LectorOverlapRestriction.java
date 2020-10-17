package ua.univ.vsynytsyn.timetable.domain.model.restrictions.impl;


import ua.univ.vsynytsyn.timetable.domain.model.Allele;

public class LectorOverlapRestriction extends OverlapRestriction {

    public LectorOverlapRestriction(double penalty) {
        super(penalty);
    }


    @Override
    long getEntityId(Allele allele) {
        return allele.getStudyBlock().getLectorID();
    }
}
