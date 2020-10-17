package ua.univ.vsynytsyn.timetable.domain.model;

import ua.univ.vsynytsyn.timetable.domain.model.restrictions.Restriction;

import java.util.ArrayList;
import java.util.List;

public class Population {

    private List<Unit> units;
    private int unitsNumber;
    private int iterations;
    private double mutationRate;
    private List<Restriction> restrictions;


    public Population(int unitsNumber, int iterations, double mutationRate, List<Restriction> restrictions) {
        this.unitsNumber = unitsNumber;
        this.iterations = iterations;
        this.mutationRate = mutationRate;
        this.restrictions = restrictions;
        units = new ArrayList<>(unitsNumber);
    }

    public Unit startSelection(){
        createInitialPopulation();
        for (int i = 0; i < iterations; i++) {
            selection();
            crossover();
            mutations();

            Unit result = checkForResult();
            if (result != null)
                return result;
        }
        return null;
    }

    private void createInitialPopulation(){

    }

    private void selection(){
//        for (int i = 0; i < unitsNumber; i++) {
//            double unitFitness
//        }
    }

    private void crossover(){

    }

    private void mutations(){

    }

    private Unit checkForResult(){
        return null;
    }
}
