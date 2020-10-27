package ua.univ.vsynytsyn.timetable.csp.valuepicker;

import ua.univ.vsynytsyn.timetable.csp.graph.CSPGraph;

public interface CSPValuePicker {
    Integer pickValue(CSPGraph graph, int node);
}
