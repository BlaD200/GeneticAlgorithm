package ua.univ.vsynytsyn.timetable.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.univ.vsynytsyn.timetable.domain.model.Population;
import ua.univ.vsynytsyn.timetable.domain.model.Unit;
import ua.univ.vsynytsyn.timetable.domain.model.restrictions.Restriction;
import ua.univ.vsynytsyn.timetable.repositories.StudyBlockRepository;
import ua.univ.vsynytsyn.timetable.service.AuditoriumService;
import ua.univ.vsynytsyn.timetable.service.TimeSlotService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/algorithm/start")
public class AlgorithmController {

    private final List<Restriction> restrictions;

    private final AuditoriumService auditoriumService;
    private final TimeSlotService timeSlotService;
    private final StudyBlockRepository studyBlockRepository;

    @Autowired
    public AlgorithmController(List<Restriction> restrictions, AuditoriumService auditoriumService,
                               TimeSlotService timeSlotService, StudyBlockRepository studyBlockRepository) {
        this.restrictions = restrictions;
        this.auditoriumService = auditoriumService;
        this.timeSlotService = timeSlotService;
        this.studyBlockRepository = studyBlockRepository;
    }


    @GetMapping
    void startAlgorithm(
            @RequestParam("unitsNumber") int unitsNumber,
            @RequestParam("iterations") int iterations,
            @RequestParam("mutationRate") double mutationRate,
            HttpServletResponse response) {
        try {
            Population population = new Population(
                    unitsNumber, iterations, mutationRate,
                    restrictions,
                    auditoriumService, timeSlotService,
                    studyBlockRepository);

            Unit unit = population.startSelection();
            if (unit == null){
                response.setStatus(HttpStatus.NO_CONTENT.value());
                return;
            }
            // get your file as InputStream
//            InputStream is = getXslxFileFromUnit(unit);
            // copy it to response's OutputStream
//            org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
//            response.setContentType("application/xlsx");
            response.flushBuffer();
        } catch (IOException ex) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

    }
}
