package ua.univ.vsynytsyn.timetable.csp.startpicker;

import ua.univ.vsynytsyn.timetable.csp.graph.CSPGraph;

public class DefaultStartPicker implements CSPStartPicker {

    @Override
    public int pickStart(CSPGraph g) {
        return 0;
    }
}
