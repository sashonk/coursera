package ru.asocial.algorithm.graph;

import edu.princeton.cs.algs4.Digraph;

public class DigraphAdapter implements IGraphAdapter{

    private Digraph graph;

    DigraphAdapter(Digraph g) {
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

