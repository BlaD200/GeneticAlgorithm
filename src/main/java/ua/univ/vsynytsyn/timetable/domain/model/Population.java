package ua.univ.vsynytsyn.timetable.domain.model;

import ua.univ.vsynytsyn.timetable.domain.entities.StudyBlock;
import ua.univ.vsynytsyn.timetable.domain.model.restrictions.Restriction;
import ua.univ.vsynytsyn.timetable.repositories.GroupsRepository;
import ua.univ.vsynytsyn.timetable.repositories.StudyBlockRepository;
import ua.univ.vsynytsyn.timetable.service.AuditoriumService;
import ua.univ.vsynytsyn.timetable.service.TimeSlotService;

import java.util.*;

public class Population {

    private List<Unit> units;
    private final int unitsNumber;
    private final int iterations;
    private final double mutationRate;
    private final List<Restriction> restrictions;

    private final AuditoriumService auditoriumService;
    private final TimeSlotService timeSlotService;
    private final StudyBlockRepository studyBlockRepository;
    private final GroupsRepository groupsRepository;


    public Population(int unitsNumber, int iterations, double mutationRate, List<Restriction> restrictions, AuditoriumService auditoriumService, TimeSlotService timeSlotService, StudyBlockRepository studyBlockRepository, GroupsRepository groupsRepository) {
        this.unitsNumber = unitsNumber;
        this.iterations = iterations;
        this.mutationRate = mutationRate;
        this.restrictions = restrictions;
        this.auditoriumService = auditoriumService;
        this.timeSlotService = timeSlotService;
        this.studyBlockRepository = studyBlockRepository;
        this.groupsRepository = groupsRepository;
    }


    public Unit startSelection() {
        createInitialPopulation();
        selection();
        for (int i = 0; i < iterations; i++) {
            crossover();
            mutations();
            selection();

            Unit result = checkForResult();
            if (result != null)
                return result;
        }
        return null;
    }


    public Unit getUnitWithLowestFitness(){
        return sortByFitness().get(units.size() - 1);
    }


    private void createInitialPopulation() {
        units = new ArrayList<>(unitsNumber);
        for (int i = 0; i < unitsNumber; ++i) {
            List<Allele> alleles = createRandomAlleles();
            units.add(Unit.builder().alleles(alleles).build());
        }
    }


    private void selection() {
        for (Unit unit : units) {
            unit.calculateFitness(restrictions);
        }
    }


    private void crossover() {
        List<Unit> sortedByFitness = sortByFitness();
        ArrayList<Unit> offsprings = new ArrayList<>(unitsNumber);

        int allelesCount = units.get(0).getAlleles().size();
        for (int i = 0; i < unitsNumber; i++) {
            Unit parent1 = Objects.requireNonNull(getRandomUnitByFitness(sortedByFitness));
            Unit parent2 = Objects.requireNonNull(getRandomUnitByFitness(sortedByFitness));

            ArrayList<Allele> offspringAlleles = new ArrayList<>(allelesCount);

            List<Allele> parent1Alleles = parent1.getAlleles();
            List<Allele> parent2Alleles = parent2.getAlleles();

            for(int k = 0, l = 0; k < allelesCount; ++k, ++l){
                StudyBlock studyBlock = parent1Alleles.get(k).getStudyBlock();
                Long auditoriumId;
                Long timeSlotId;

                if (selectFromPair() == 0)
                    auditoriumId = parent1Alleles.get(k).getAuditoriumID();
                else
                    auditoriumId = parent2Alleles.get(l).getAuditoriumID();

                if (selectFromPair() == 0)
                    timeSlotId = parent1Alleles.get(k).getTimeSlotID();
                else
                    timeSlotId = parent2Alleles.get(l).getTimeSlotID();

                Allele allele = Allele.builder()
                        .studyBlock(studyBlock)
                        .auditoriumID(auditoriumId)
                        .timeSlotID(timeSlotId)
                        .build();
                offspringAlleles.add(k, allele);
            }

            Unit offspring = new Unit.UnitBuilder().alleles(offspringAlleles).build();
            offsprings.add(i, offspring);
        }
        this.units = offsprings;
    }


    private void mutations() {
        for (int i = 0; i < Math.round(mutationRate * unitsNumber); i++) {
            int randomUnitIndex = (int) (Math.random() * units.size());
            Unit unit = units.get(randomUnitIndex);
            List<Allele> alleles = unit.getAlleles();
            for (long j = 0; j < Math.round(mutationRate * alleles.size()); j++) {
                int randomAlleleIndex = (int) (Math.random() * units.size());
                Allele allele = alleles.get(randomAlleleIndex);
                if (selectFromPair() == 0)
                    allele.setAuditoriumID(auditoriumService.getRandomAuditoriumId());
                else
                    allele.setTimeSlotID(timeSlotService.getRandomTimeSlotId());
            }
        }
    }


    private Unit checkForResult() {
        for (Unit unit : units) {
            if (unit.getFitness() == 0)
                return unit;
        }
        return null;
    }


    private List<Allele> createRandomAlleles() {
        List<StudyBlock> studyBlocks = studyBlockRepository.findAll();
        Allele[] alleles = new Allele[studyBlocks.size()];

        for (int i = 0; i < studyBlocks.size(); i++) {
            Allele allele = Allele
                    .builder()
                    .studyBlock(studyBlocks.get(i))
                    .timeSlotID(timeSlotService.getRandomTimeSlotId())
                    .auditoriumID(auditoriumService.getRandomAuditoriumId())
                    .build();
            alleles[i] = allele;
        }
        return Arrays.asList(alleles);
    }


    private List<Unit> sortByFitness(){
        Unit[] unitsCopy = new Unit[unitsNumber];
        System.arraycopy(this.units.toArray(new Unit[unitsNumber]), 0, unitsCopy, 0, this.units.size());
        List<Unit> sortedUnits = Arrays.asList(unitsCopy);
        sortedUnits.sort(Comparator.comparingDouble(Unit::getFitness));
        return sortedUnits;
    }


    private Unit getRandomUnitByFitness(List<Unit> sortedUnits){
        double randVal = Math.random() * 100;
        for (Unit sortedUnit : sortedUnits) {
            if (sortedUnit.getFitness() <= randVal)
                return sortedUnit;
        }
        return getRandomUnit();
    }

    public Unit getRandomUnit() {
        int randomIndex = (int) (Math.random() * units.size());
        return units.get(randomIndex);
    }


    private int selectFromPair() {
        return Math.random() < .50 ? 0 : 1;
    }
}
