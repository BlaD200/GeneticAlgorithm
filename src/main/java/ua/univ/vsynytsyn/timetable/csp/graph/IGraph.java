package ua.univ.vsynytsyn.timetable.csp.graph;

public interface IGraph {
    void addEdge(int v, int w);

    int degree(int v);

    Iterable<Integer> adj(int v);

    int getV();
}
