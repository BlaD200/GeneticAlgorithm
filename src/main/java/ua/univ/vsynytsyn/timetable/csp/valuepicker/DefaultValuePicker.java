package ua.univ.vsynytsyn.timetable.csp.valuepicker;

import ua.univ.vsynytsyn.timetable.csp.graph.CSPGraph;

import java.util.List;

public class DefaultValuePicker implements CSPValuePicker {

    @Override
    public Integer pickValue(CSPGraph graph, int node) {
        List<Integer> availableValues = graph.getAvailableValues(node);

        return availableValues.size() == 0 ? -1 : availableValues.get(0);
    }
}
