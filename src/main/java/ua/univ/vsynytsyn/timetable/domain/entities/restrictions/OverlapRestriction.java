package ua.univ.vsynytsyn.timetable.domain.entities.restrictions;

import ua.univ.vsynytsyn.timetable.domain.data.Allele;
import ua.univ.vsynytsyn.timetable.domain.data.Unit;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

abstract public class OverlapRestriction implements Restriction {

    private final double penalty;

    public OverlapRestriction(double penalty) {
        this.penalty = penalty;
    }

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

            long id = getEntityId(allele);
            if (!seen.add(id)) {
                result += penalty;
            }
        }

        return result;
    }

    abstract long getEntityId(Allele allele);
}
