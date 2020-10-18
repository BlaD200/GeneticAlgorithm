package ua.univ.vsynytsyn.timetable.domain.model;

import lombok.Builder;
import lombok.Data;
import ua.univ.vsynytsyn.timetable.domain.model.restrictions.Restriction;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class Unit {

    @NotNull
    private List<Allele> alleles;
    private Double fitness;

    public double calculateFitness(List<Restriction> restrictions){
        fitness = 0.;
        for (Restriction restriction : restrictions) {
            fitness += restriction.calculatePenalty(this);
        }
        return fitness;
    }
}
