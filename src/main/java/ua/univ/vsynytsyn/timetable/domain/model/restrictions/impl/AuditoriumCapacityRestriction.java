package ua.univ.vsynytsyn.timetable.domain.model.restrictions.impl;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.univ.vsynytsyn.timetable.domain.entities.Auditorium;
import ua.univ.vsynytsyn.timetable.domain.model.Allele;
import ua.univ.vsynytsyn.timetable.domain.model.Unit;
import ua.univ.vsynytsyn.timetable.domain.model.restrictions.Restriction;
import ua.univ.vsynytsyn.timetable.exceptions.AuditoriumNotFoundException;
import ua.univ.vsynytsyn.timetable.repositories.AuditoriumRepository;

import java.util.List;
import java.util.Optional;

@Component
public class AuditoriumCapacityRestriction implements Restriction {

    private final double penalty = 1;

    private final AuditoriumRepository auditoriumRepository;

    @Autowired
    public AuditoriumCapacityRestriction(AuditoriumRepository auditoriumRepository) {
        this.auditoriumRepository = auditoriumRepository;
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
            throw new AuditoriumNotFoundException();
        }
    }
}
