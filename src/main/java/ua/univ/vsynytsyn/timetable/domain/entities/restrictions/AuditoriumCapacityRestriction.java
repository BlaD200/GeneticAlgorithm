package ua.univ.vsynytsyn.timetable.domain.entities.restrictions;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import ua.univ.vsynytsyn.timetable.domain.data.Allele;
import ua.univ.vsynytsyn.timetable.domain.data.Unit;
import ua.univ.vsynytsyn.timetable.domain.entities.Auditorium;
import ua.univ.vsynytsyn.timetable.exceptions.AuditoriumNotFound;
import ua.univ.vsynytsyn.timetable.repositories.AuditoriumRepository;

import java.util.List;
import java.util.Optional;

public class AuditoriumCapacityRestriction implements Restriction {

    private final double penalty;

    @Autowired
    private AuditoriumRepository auditoriumRepository;

    public AuditoriumCapacityRestriction(double penalty) {
        this.penalty = penalty;
    }

    @Override
    public double calculatePenalty(Unit unit) {
        double result = 0;

        List<Allele> alleles = unit.getAlleles();
        for (Allele allele : alleles) {
            if (allele.getStudyBlock().getStudentsCount() > capacity(allele.getAuditoriumID())) {
                result += penalty;
            }
        }

        return result;
    }

    @SneakyThrows
    int capacity(long auditoryId) {
        Optional<Auditorium> optionalAuditorium = auditoriumRepository.findById(auditoryId);
        if (optionalAuditorium.isPresent()) {
            return optionalAuditorium.get().getSpace();
        } else {
            throw new AuditoriumNotFound();
        }
    }
}