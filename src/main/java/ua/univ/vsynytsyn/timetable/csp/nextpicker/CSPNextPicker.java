package ua.univ.vsynytsyn.timetable.csp.nextpicker;

import ua.univ.vsynytsyn.timetable.csp.graph.CSPGraph;

public interface CSPNextPicker {
    Integer pickNext(CSPGraph graph);
}
