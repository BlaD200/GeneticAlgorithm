package ua.univ.vsynytsyn.timetable.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.univ.vsynytsyn.timetable.csp.CSP;
import ua.univ.vsynytsyn.timetable.csp.filtering.ForwardChecking;
import ua.univ.vsynytsyn.timetable.csp.graph.CSPGraph;
import ua.univ.vsynytsyn.timetable.csp.graph.Graph;
import ua.univ.vsynytsyn.timetable.csp.nextpicker.MRVNextPicker;
import ua.univ.vsynytsyn.timetable.csp.startpicker.StartDegreeHeuristic;
import ua.univ.vsynytsyn.timetable.csp.valuepicker.LCVPicker;
import ua.univ.vsynytsyn.timetable.domain.data.LessonType;
import ua.univ.vsynytsyn.timetable.domain.entities.Auditorium;
import ua.univ.vsynytsyn.timetable.domain.entities.StudyBlock;
import ua.univ.vsynytsyn.timetable.domain.entities.TimeSlot;
import ua.univ.vsynytsyn.timetable.repositories.AuditoriumRepository;
import ua.univ.vsynytsyn.timetable.repositories.StudyBlockRepository;
import ua.univ.vsynytsyn.timetable.repositories.TimeSlotRepository;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/csp/algorithm/start")
public class CSPController {

    private final StudyBlockRepository studyBlockRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final AuditoriumRepository auditoriumRepository;

    private List<Long> blocks;
    private int[][] auditoriumsResult;

    @Autowired
    public CSPController(StudyBlockRepository studyBlockRepository, TimeSlotRepository timeSlotRepository, AuditoriumRepository auditoriumRepository) {
        this.studyBlockRepository = studyBlockRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.auditoriumRepository = auditoriumRepository;
    }

    @GetMapping
    public boolean startAlgorithm(@RequestParam("maxSteps") int maxSteps) {
        List<TimeSlot> timeSlots = timeSlotRepository.findAll();

        List<StudyBlock> studyBlocks = studyBlockRepository.findAll();
        blocks = studyBlocks.stream().map(StudyBlock::getStudyBlockID).collect(Collectors.toList());

        Graph graph = new Graph(studyBlocks.size());
        lectorsConstraints(graph, studyBlocks);
        lecturesConstraints(graph, studyBlocks);

        int v = graph.getV();
        int maxValue = timeSlots.size();
        CSPGraph cspGraph = new CSPGraph(graph, maxValue,
                generateAvailableValues(v, maxValue),
                generateLists(v),
                generateLists(v));


        CSP csp = new CSP(cspGraph, new StartDegreeHeuristic(), new LCVPicker(), new ForwardChecking(), new MRVNextPicker());
        if (csp.run(maxSteps)) {
            while (!checkAuditoriums(cspGraph, maxSteps)) {
                if (!csp.continueRun(maxSteps)) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    private boolean checkAuditoriums(CSPGraph graph, int maxSteps) {
        List<Auditorium> auditoriums = auditoriumRepository.findAll();

        int[] values = graph.getValues();
        List<Integer>[] sameTime = generateLists(graph.getMaxValue());
        for (int i = 0; i < values.length; i++) {
            int time = values[i];
            sameTime[time].add(i);
        }

        for (List<Integer> studyBlocks : sameTime) {
            if (!checkAuditoriums(studyBlocks, auditoriums, maxSteps))
                return false;
        }

        return true;
    }

    private boolean checkAuditoriums(List<Integer> studyBlocks, List<Auditorium> auditoriums, int maxSteps) {
        if (studyBlocks.isEmpty())
            return true;

        List<Long> auditoriumsIds = auditoriums.stream().map(Auditorium::getAuditoriumID).collect(Collectors.toList());
        List<Integer>[] prohibited = generateProhibitedAuditoriums(studyBlocks, auditoriums, auditoriumsIds);

        Graph graph = new Graph(studyBlocks.size());
        for (int i = 0; i < studyBlocks.size(); i++) {
            for (int j = 0; j < studyBlocks.size(); j++) {
                graph.addEdge(i, j);
            }
        }

        int v = graph.getV();
        int maxValue = auditoriums.size();
        CSPGraph cspGraph = new CSPGraph(graph, maxValue,
                generateAvailableValues(v, maxValue),
                generateLists(v),
                prohibited);

        CSP csp = new CSP(cspGraph, new StartDegreeHeuristic(), new LCVPicker(), new ForwardChecking(), new MRVNextPicker());

        return csp.run(maxSteps);
    }

    private void lecturesConstraints(Graph graph, List<StudyBlock> studyBlocks) {
        List<Long> lectures = studyBlocks
                .stream()
                .filter(b -> b.getLessonType().equals(LessonType.LECTURE))
                .map(StudyBlock::getStudyBlockID)
                .collect(Collectors.toList());
        for (Long lecture : lectures) {
            int index = blocks.indexOf(lecture);
            for (int j = 0; j < graph.getV(); j++) {
                graph.addEdge(index, j);
            }
        }
    }

    private void lectorsConstraints(Graph graph, List<StudyBlock> studyBlocks) {
        List<Long> lectorsIds = studyBlocks
                .stream()
                .map(StudyBlock::getLectorID)
                .distinct()
                .collect(Collectors.toList());
        for (Long id : lectorsIds) {
            List<StudyBlock> sameLector = studyBlocks
                    .stream()
                    .filter(b -> b.getLectorID().equals(id))
                    .collect(Collectors.toList());
            for (int j = 0; j < sameLector.size(); j++) {
                for (int k = 0; k < sameLector.size(); k++) {
                    int l = blocks.indexOf(sameLector.get(j).getStudyBlockID());
                    int r = blocks.indexOf(sameLector.get(k).getStudyBlockID());
                    graph.addEdge(l, r);
                }
            }
        }
    }

    private List<Integer>[] generateProhibitedAuditoriums(List<Integer> studyBlocks,
                                                          List<Auditorium> auditoriums,
                                                          List<Long> auditoriumsIds) {
        List<Integer>[] prohibitedAuditoriums = generateLists(studyBlocks.size());
        for (int i = 0; i < studyBlocks.size(); i++) {
            StudyBlock studyBlock = studyBlockRepository.findById(blocks.get(studyBlocks.get(i))).get();
            List<Integer> prohibited = auditoriums
                    .stream()
                    .filter(a -> a.getSpace() < studyBlock.getStudentsCount())
                    .map(Auditorium::getAuditoriumID)
                    .map(auditoriumsIds::indexOf)
                    .collect(Collectors.toList());
            prohibitedAuditoriums[i].addAll(prohibited);
        }

        return prohibitedAuditoriums;
    }

    private static List<Integer>[] generateAvailableValues(int v, int maxValue) {
        List<Integer>[] lists = generateLists(v);
        for (List<Integer> list : lists) {
            for (int i = 0; i < maxValue; i++) {
                list.add(i);
            }
        }

        return lists;
    }

    private static List<Integer>[] generateLists(int n) {
        List<Integer>[] lists = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            lists[i] = new ArrayList<>();
        }

        return lists;
    }
}
