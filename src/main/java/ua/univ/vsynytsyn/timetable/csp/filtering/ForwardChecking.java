package ua.univ.vsynytsyn.timetable.csp.filtering;

import ua.univ.vsynytsyn.timetable.csp.graph.CSPGraph;

import java.util.Set;

public class ForwardChecking implements CSPFiltering {

    @Override
    public void filter(CSPGraph graph, int lastModifiedNode) {
        Integer value = graph.getValue(lastModifiedNode);
        Set<Integer> adj = graph.adj(lastModifiedNode);

        for (Integer a : adj) {
            graph.getAvailableValues(a).remove(value);
        }
    }

    @Override
    public void undo(CSPGraph graph, int node, int value) {
        Set<Integer> adj = graph.adj(node);

        for (Integer a : adj) {
            graph.getAvailableValues(a).add(value);
        }
    }
}
