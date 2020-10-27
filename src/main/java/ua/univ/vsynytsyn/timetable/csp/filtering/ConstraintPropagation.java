package ua.univ.vsynytsyn.timetable.csp.filtering;

import ua.univ.vsynytsyn.timetable.csp.graph.CSPGraph;

import java.util.Set;

public class ConstraintPropagation implements CSPFiltering {

    @Override
    public void filter(CSPGraph graph, int lastModifiedNode) {
        filterNode(graph, lastModifiedNode, graph.getValue(lastModifiedNode));

        processNodeWithOneAvailableValue(graph);
    }

    @Override
    public void undo(CSPGraph graph, int node, int value) {

    }

    private void processNodeWithOneAvailableValue(CSPGraph graph) {
        boolean[] visited = new boolean[graph.getV()];

        boolean stop = false;
        while (!stop) {
            stop = true;
            for (int i = 0; i < graph.getV(); i++) {
                if (!visited[i] && graph.getAvailableValues(i).size() == 1) {
                    visited[i] = true;
                    filterNode(graph, i, graph.getAvailableValues(i).get(0));
                    stop = false;
                    break;
                }
            }
        }
    }

    public void filterNode(CSPGraph graph, int node, int value) {
        Set<Integer> adj = graph.adj(node);

        for (Integer a : adj) {
            graph.getAvailableValues(a).remove(value);
        }
    }
}
