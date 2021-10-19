package ru.asocial.algorithm.graph;
import edu.princeton.cs.algs4.Queue;

public class CC {
    private final boolean[] marked;
    private final int[] id;
    private int count;
    private boolean acylic = true;
    private int[] colors;
    private boolean bypartite = true;
    private final IGraphAdapter graph;
    private Queue<Integer> result = new Queue<>();

    public boolean isAcylic() {
        return acylic;
    }

    public boolean isBypartite() {
        return bypartite;
    }

    public CC(IGraphAdapter G)
    {
        this.graph = G;
        marked = new boolean[graph.V()];
        id = new int[graph.V()];
        colors = new int[graph.V()];
        for (int s = 0; s < graph.V(); s++) {
            if (!marked[s]) {
                dfs(graph, s, 1);
                count++;
            }
        }
    }

    Integer getNext() {
        if (!result.isEmpty()) {
            return result.dequeue();
        }
        return null;
    }

    private void dfs(IGraphAdapter G, int v, int color)
    {
        marked[v] = true;
        System.out.println("marked " + v);
        result.enqueue(v);
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

    public boolean connected(int v, int w) {
        return id[v] == id[w];
    }
    public int id(int v) {
        return id[v];
    }
    public int count() {
        return count;
    }

}