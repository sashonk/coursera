package ru.asocial.algorithm.graph;

import edu.princeton.cs.algs4.Graph;

public class GraphAdapter implements IGraphAdapter{

    private Graph graph;

    GraphAdapter(Graph g) {
        this.graph = g;
    }

    @Override
    public int V() {
        return graph.V();
    }

    @Override
    public Iterable<Integer> adj(int V) {
        return graph.adj(V);
    }
}

