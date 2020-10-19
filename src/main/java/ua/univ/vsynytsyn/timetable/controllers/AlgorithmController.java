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
import ua.univ.vsynytsyn.timetable.service.AlgorithmService;
import ua.univ.vsynytsyn.timetable.service.AuditoriumService;
import ua.univ.vsynytsyn.timetable.service.TimeSlotService;
import ua.univ.vsynytsyn.timetable.utils.XlsxUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/algorithm/start")
public class AlgorithmController {

    private final List<Restriction> restrictions;

    private final AuditoriumService auditoriumService;
    private final TimeSlotService timeSlotService;
    private final AlgorithmService algorithmService;
    private final XlsxUtils xlsxUtils;
    private final String xlsxPath;

    @Autowired
    public AlgorithmController(List<Restriction> restrictions,
                               AuditoriumService auditoriumService,
                               TimeSlotService timeSlotService,
                               AlgorithmService algorithmService,
                               XlsxUtils xlsxUtils) {
        this.restrictions = restrictions;
        this.auditoriumService = auditoriumService;
        this.timeSlotService = timeSlotService;
        this.algorithmService = algorithmService;
        this.xlsxUtils = xlsxUtils;

        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        xlsxPath = path.substring(0, path.length() - 1) + "temp.xlsx";
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
                    auditoriumService, timeSlotService, algorithmService
            );

            Unit unit = population.startSelection();
            if (unit == null) {
                response.setStatus(HttpStatus.NO_CONTENT.value());
                return;
            }
            // get your file as InputStream
            xlsxUtils.createXlsx(unit, xlsxPath);
            InputStream is = new FileInputStream(xlsxPath);
            // copy it to response's OutputStream
            org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
            response.setContentType("application/xlsx");
            response.flushBuffer();
        } catch (IOException ex) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

    }
}
