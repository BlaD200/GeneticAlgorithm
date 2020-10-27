package ua.univ.vsynytsyn.timetable.csp.filtering;

import ua.univ.vsynytsyn.timetable.csp.graph.CSPGraph;

public class NoFiltering implements CSPFiltering {

    @Override
    public void filter(CSPGraph graph, int lastModifiedNode) {
        // no filtering
    }

    @Override
    public void undo(CSPGraph graph, int node, int value) {
        // nothing to undo
    }
}
