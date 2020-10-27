package ua.univ.vsynytsyn.timetable.csp.nextpicker;

import ua.univ.vsynytsyn.timetable.csp.graph.CSPGraph;

public class DefaultNextPicker implements CSPNextPicker {

    @Override
    public Integer pickNext(CSPGraph graph) {
        for (int i = 0; i < graph.getV(); i++) {
            if (!graph.hasValue(i))
                return i;
        }

        throw new RuntimeException("Every node has a value");
    }
}
