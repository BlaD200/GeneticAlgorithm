package ua.univ.vsynytsyn.timetable.csp;

import ua.univ.vsynytsyn.timetable.csp.filtering.CSPFiltering;
import ua.univ.vsynytsyn.timetable.csp.graph.CSPGraph;
import ua.univ.vsynytsyn.timetable.csp.nextpicker.CSPNextPicker;
import ua.univ.vsynytsyn.timetable.csp.startpicker.CSPStartPicker;
import ua.univ.vsynytsyn.timetable.csp.valuepicker.CSPValuePicker;

import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class CSP {

    private final CSPGraph graph;
    private final CSPStartPicker startPicker;
    private final CSPValuePicker valuePicker;
    private final CSPFiltering filter;
    private final CSPNextPicker nextPicker;

    private final Stack<Integer> history;
    private int next;

    private int steps = 0;

    public CSP(CSPGraph graph,
               CSPStartPicker startPicker,
               CSPValuePicker valuePicker,
               CSPFiltering filter,
               CSPNextPicker nextPicker) {
        this.graph = graph;
        this.startPicker = startPicker;
        this.valuePicker = valuePicker;
        this.filter = filter;
        this.nextPicker = nextPicker;
        history = graph.getHistory();
        next = startPicker.pickStart(graph);
        history.push(next);
    }

    public boolean run(int maxSteps) {
        this.steps = 0;

        while (steps++ < maxSteps) {
            int value = valuePicker.pickValue(graph, next);
            if (value == -1) {
                Integer node = history.pop();
                graph.removeAllChecked(node);

                if (history.empty())
                    return false;

                next = history.peek();
                int prevValue = graph.getValue(next);
                graph.setChecked(next, prevValue);
                filter.undo(graph, next, prevValue);
            } else if (checkValues(next, value)) {
                graph.setChecked(next, value);
                filter.undo(graph, next, value);
            } else {
                graph.setValue(next, value);
                if (stop()) {
                    return true;
                }
                filter.filter(graph, next);
                next = nextPicker.pickNext(graph);
                history.push(next);
            }
        }

        return false;
    }

    public boolean continueRun(int maxSteps) {
        graph.setChecked(next, graph.getValue(next));
        return run(maxSteps);
    }

    private boolean checkValues(int next, int value) {
        List<Integer> adjValues = graph.adj(next)
                .stream()
                .map(graph::getValue)
                .collect(Collectors.toList());
        return adjValues.contains(value);
    }

    private boolean stop() {
        for (int i = 0; i < graph.getV(); i++) {
            if (!graph.hasValue(i)) {
                return false;
            }
        }

        return true;
    }

    public int getSteps() {
        return steps;
    }
}
