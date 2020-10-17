package ua.univ.vsynytsyn.timetable.domain.model;

import lombok.Data;
import ua.univ.vsynytsyn.timetable.domain.model.restrictions.Restriction;

import java.util.List;

@Data
public class Unit {

    private List<Allele> alleles;
    private Double fitness;

    public Unit(List<Allele> alleles){
        this.alleles = alleles;
    }

    private double calculateFitness(List<Restriction> restrictions){
        fitness = 0.;
        for (Restriction restriction : restrictions) {
            fitness += restriction.calculatePenalty(this);
        }
        return fitness;
    }
}
