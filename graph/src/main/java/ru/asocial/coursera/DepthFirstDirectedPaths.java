package ru.asocial.coursera;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Stack;

import java.util.Iterator;

public class DepthFirstDirectedPaths {
    private boolean[] marked;
    private int[] edgeTo;
    private final int s;
    private EventListener listener;

    public DepthFirstDirectedPaths(Digraph G, int s, EventListener eventListener) {
        this.listener = eventListener;
        this.marked = new boolean[G.V()];
        this.edgeTo = new int[G.V()];
        this.s = s;
        this.validateVertex(s);
        this.dfs(G, s);
    }

    private void dfs(Digraph G, int v) {
        this.marked[v] = true;
        listener.marked(v );
        Iterator var3 = G.adj(v).iterator();

        while(var3.hasNext()) {
            int w = (Integer)var3.next();
            if (!this.marked[w]) {
                this.edgeTo[w] = v;
                listener.edgeTo(w , v );
                this.dfs(G, w);
            }
        }
    }

    public boolean hasPathTo(int v) {
        this.validateVertex(v);
        return this.marked[v];
    }

    public Iterable<Integer> pathTo(int v) {
        this.validateVertex(v);
        if (!this.hasPathTo(v)) {
            return null;
        } else {
            Stack<Integer> path = new Stack();

            for(int x = v; x != this.s; x = this.edgeTo[x]) {
                path.push(x);
            }

            path.push(this.s);
            return path;
        }
    }

    private void validateVertex(int v) {
        int V = this.marked.length;
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }
    }
}
