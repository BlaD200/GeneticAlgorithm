package ua.univ.vsynytsyn.timetable.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.univ.vsynytsyn.timetable.repositories.AuditoriumRepository;

@Service
public class AuditoriumService {

    private final AuditoriumRepository repository;

    @Autowired
    public AuditoriumService(AuditoriumRepository repository) {
        this.repository = repository;
    }

    public Long getRandomAuditoriumId() {
        int randomIndex = (int) (Math.random() * repository.count());
        return repository.findAll().get(randomIndex).getAuditoriumID();
    }
}
