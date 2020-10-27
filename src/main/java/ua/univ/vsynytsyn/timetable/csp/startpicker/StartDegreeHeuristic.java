package ua.univ.vsynytsyn.timetable.csp.startpicker;

import ua.univ.vsynytsyn.timetable.csp.graph.CSPGraph;

public class StartDegreeHeuristic implements CSPStartPicker {

    @Override
    public int pickStart(CSPGraph g) {
        int maxDegreeNode = 0;
        int maxDegree = g.degree(0);
        for (int i = 1; i < g.getV(); i++) {
            int degree = g.degree(i);
            if (degree > maxDegree) {
                maxDegree = degree;
                maxDegreeNode = i;
            }
        }

        return maxDegreeNode;
    }
}
