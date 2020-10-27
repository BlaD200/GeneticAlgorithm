package ua.univ.vsynytsyn.timetable.csp.filtering;

import ua.univ.vsynytsyn.timetable.csp.graph.CSPGraph;

public interface CSPFiltering {
    void filter(CSPGraph graph, int lastModifiedNode);

    void undo(CSPGraph graph, int node, int value);
}
