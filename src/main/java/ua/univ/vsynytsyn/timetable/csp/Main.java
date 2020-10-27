package ua.univ.vsynytsyn.timetable.csp;

import ua.univ.vsynytsyn.timetable.csp.filtering.ForwardChecking;
import ua.univ.vsynytsyn.timetable.csp.graph.CSPGraph;
import ua.univ.vsynytsyn.timetable.csp.graph.Graph;
import ua.univ.vsynytsyn.timetable.csp.nextpicker.MRVNextPicker;
import ua.univ.vsynytsyn.timetable.csp.startpicker.StartDegreeHeuristic;
import ua.univ.vsynytsyn.timetable.csp.valuepicker.LCVPicker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// TODO make available values set
public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph(9);
        graph.addEdge(0, 1);
        graph.addEdge(0, 3);
        graph.addEdge(0, 4);

        graph.addEdge(1, 2);
        graph.addEdge(1, 4);
        graph.addEdge(1, 5);

        graph.addEdge(2, 5);

        graph.addEdge(3, 4);
        graph.addEdge(3, 6);
        graph.addEdge(3, 7);

        graph.addEdge(4, 5);
        graph.addEdge(4, 7);
        graph.addEdge(4, 7);

        graph.addEdge(5, 8);

        graph.addEdge(6, 7);

        graph.addEdge(7, 8);

        int v = graph.getV();
        int maxValue = 3;
        int maxSteps = 1000;
        CSPGraph cspGraph = new CSPGraph(graph, maxValue,
                generateAvailableValues(v, maxValue),
                generateLists(v),
                generateLists(v));

        CSP csp = new CSP(cspGraph, new StartDegreeHeuristic(), new LCVPicker(), new ForwardChecking(), new MRVNextPicker());

        csp.run(maxSteps);
        System.out.println("Steps: " + csp.getSteps());
        System.out.println(Arrays.toString(cspGraph.getValues()));

        csp.continueRun(maxSteps);
        System.out.println(Arrays.toString(cspGraph.getValues()));
    }

    private static List<Integer>[] generateLists(int n) {
        List<Integer>[] lists = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            lists[i] = new ArrayList<>();
        }

        return lists;
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
}
