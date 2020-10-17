package ua.univ.vsynytsyn.timetable.domain.data;

import lombok.Data;

import java.util.List;

@Data
public class Unit {

    private List<Allele> alleles;
    private Double fitness;

    public Unit(List<Allele> alleles){
        this.alleles = alleles;
    }

//    private double calculateFitness(List<Restriction>){
//
//    }
}
