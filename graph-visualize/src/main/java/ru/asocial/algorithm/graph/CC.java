package ru.asocial.algorithm.graph;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.Stack;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class CC {
    private boolean[] marked;
    private int[] id;
    private int count;
    private boolean acylic = true;
    private int[] colors;
    private boolean bypartite = true;
    private Graph graph;
    private Integer verticeIndex;
    private final Object monitor = new Object();

    public CC(Graph G)
    {
        this.graph = G;
        marked = new boolean[graph.V()];
        id = new int[graph.V()];
        colors = new int[graph.V()];
        verticeIndex = null;
    }

    void start() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int s = 0; s < graph.V(); s++) {
                    if (!marked[s]) {
                        try {
                            dfs(graph, s, 1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        count++;
                    }
                }
            }
        });

        t.start();
    }

    Integer getNext() throws InterruptedException {
        synchronized (monitor){
            monitor.notify();
            return verticeIndex;
        }
    }

    private void dfs(Graph G, int v, int color) throws InterruptedException
    {
        marked[v] = true;
        synchronized (monitor) {
            verticeIndex = v;
            monitor.wait();
            verticeIndex = null;
        }
        id[v] = count;
        colors[v] = color;
        int markedCount = 0;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w, color == 1 ? 2 : 1);
            }
            else {
                markedCount++;
                if (markedCount >= 2) {
                    acylic = false;
                }

                if (colors[w] == colors[v]) {
                    bypartite = false;
                }
            }
        }
    }
    public boolean isBypartite() {
        return bypartite;
    }
    public boolean connected(int v, int w) {
        return id[v] == id[w];
    }
    public int id(int v) {
        return id[v];
    }
    public int count() {
        return count;
    }
    public boolean isAcylic() {
        return acylic;
    }

}