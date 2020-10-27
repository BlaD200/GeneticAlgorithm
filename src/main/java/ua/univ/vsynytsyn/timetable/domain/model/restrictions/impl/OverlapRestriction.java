package ua.univ.vsynytsyn.timetable.domain.model.restrictions.impl;

import ua.univ.vsynytsyn.timetable.domain.model.Allele;
import ua.univ.vsynytsyn.timetable.domain.model.Unit;
import ua.univ.vsynytsyn.timetable.domain.model.restrictions.Restriction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

abstract public class OverlapRestriction implements Restriction {

    @Override
    public double calculatePenalty(Unit unit) {
        if (unit.getAlleles().size() < 1)
            return 0;

        double result = 0;

        List<Allele> alleles = new ArrayList<>(unit.getAlleles());
        alleles.sort(Comparator.comparingLong(Allele::getTimeSlotID));

        long currentTime = alleles.get(0).getTimeSlotID();
        HashSet<Long> seen = new HashSet<>();
        for (Allele allele : alleles) {
            if (allele.getTimeSlotID() != currentTime) {
                currentTime = allele.getTimeSlotID();
                seen = new HashSet<>();
            }

            List<Long> ids = getEntityIds(allele);
            for (Long id : ids) {
                if (!seen.add(id)) {
                    result += getPenalty();
                }
            }
        }

        return result;
    }

    abstract List<Long> getEntityIds(Allele allele);

    abstract double getPenalty();
}
