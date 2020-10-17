package ua.univ.vsynytsyn.timetable.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.univ.vsynytsyn.timetable.repositories.TimeSlotRepository;

@Service
public class TimeSlotService {

    private final TimeSlotRepository repository;


    @Autowired
    public TimeSlotService(TimeSlotRepository repository) {
        this.repository = repository;
    }


    public Long getRandomTimeSlotId() {
        int randomIndex = (int) (Math.random() * repository.count());
        return repository.findAll().get(randomIndex).getTimeslotID();
    }
}
