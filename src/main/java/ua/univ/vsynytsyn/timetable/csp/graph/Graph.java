package ua.univ.vsynytsyn.timetable.csp.graph;

import java.util.HashSet;
import java.util.Set;

public class Graph implements IGraph {

    private final int v;
    private final Set<Integer>[] adj;

    public Graph(int v) {
        this.v = v;
        adj = (Set<Integer>[]) new HashSet[v];
        for (int i = 0; i < v; i++)
            adj[i] = new HashSet<>();
    }

    public void addEdge(int v, int w) {
        if (v != w) {
            adj[v].add(w);
            adj[w].add(v);
        }
    }

    public int degree(int v) {
        int degree = 0;
        for (int ignored : adj(v))
            degree++;
        return degree;
    }

    public Set<Integer> adj(int v) {
        return adj[v];
    }

    public int getV() {
        return v;
    }
}

