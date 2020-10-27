package ua.univ.vsynytsyn.timetable.csp.graph;

import java.util.*;

public class CSPGraph implements IGraph {

    private final Graph graph;
    private final int maxValue;
    private final int[] values;
    private final List<Integer>[] availableValues;
    private final List<Integer>[] checkedValues;
    private final List<Integer>[] prohibitedValues;
    private final Stack<Integer> history = new Stack<>();

    public CSPGraph(Graph graph, int maxValue, List<Integer>[] availableValues, List<Integer>[] checkedValues, List<Integer>[] prohibitedValues) {
        this.graph = graph;
        this.maxValue = maxValue;
        this.values = new int[graph.getV()];
        Arrays.fill(values, -1);
        this.availableValues = availableValues;
        this.checkedValues = checkedValues;
        this.prohibitedValues = prohibitedValues;
    }

    @Override
    public void addEdge(int v, int w) {
        graph.addEdge(v, w);
    }

    @Override
    public int degree(int v) {
        return graph.degree(v);
    }

    @Override
    public Set<Integer> adj(int v) {
        return graph.adj(v);
    }

    @Override
    public int getV() {
        return graph.getV();
    }

    public int getMaxValue() {
        return maxValue;
    }

    public int[] getValues() {
        return values;
    }

    public int getValue(int v) {
        return values[v];
    }

    public boolean hasValue(int v) {
        return values[v] != -1;
    }

    public List<Integer> getAvailableValues(int v) {
        ArrayList<Integer> available = new ArrayList<>(availableValues[v]);
        available.removeAll(checkedValues[v]);
        available.removeAll(prohibitedValues[v]);
        return available;
    }

    public Stack<Integer> getHistory() {
        return history;
    }

    public void setChecked(int node, int value) {
        checkedValues[node].add(value);
    }

    public void removeAllChecked(Integer node) {
        checkedValues[node] = new ArrayList<>();
    }

    public void setValue(int node, int value) {
        values[node] = value;
    }
}
