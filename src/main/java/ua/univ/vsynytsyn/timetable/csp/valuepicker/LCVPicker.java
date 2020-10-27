package ua.univ.vsynytsyn.timetable.csp.valuepicker;

import ua.univ.vsynytsyn.timetable.csp.graph.CSPGraph;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class LCVPicker implements CSPValuePicker {

    @Override
    public Integer pickValue(CSPGraph graph, int node) {
        List<Integer> availableValues = graph.getAvailableValues(node);

        if (availableValues.size() == 0) {
            return -1;
        } else if (availableValues.size() == 1) {
            return availableValues.get(0);
        }

        Set<Integer> adj = graph.adj(node);

        List<Integer> adjWithoutValue = adj
                .stream()
                .filter(a -> graph.getValue(a) == -1)
                .collect(Collectors.toList());

        return getLeastConstraintValue(graph, availableValues, adjWithoutValue);
    }

    private Integer getLeastConstraintValue(CSPGraph graph, List<Integer> values, List<Integer> adj) {
        int minValue = values.get(0);
        int minConstraints = getConstraints(graph, minValue, adj);
        for (int i = 1; i < values.size(); i++) {
            int value = values.get(i);
            int constraints = getConstraints(graph, value, adj);
            if (constraints < minConstraints) {
                minValue = value;
                minConstraints = constraints;
            }
        }

        return minValue;
    }

    private Integer getConstraints(CSPGraph graph, int value, List<Integer> adjs) {
        Integer constrains = 0;

        for (Integer adj : adjs) {
            List<Integer> availableValues = graph.getAvailableValues(adj);
            if (availableValues.contains(adj)) {
                constrains++;
            }
        }

        return constrains;
    }
}
