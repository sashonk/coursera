import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class CC {
    private boolean[] marked;
    private int[] id;
    private int count;
    private boolean acylic = true;
    private int[] colors;
    private boolean bypartite = true;
    public CC(Graph G)
    {
        marked = new boolean[G.V()];
        id = new int[G.V()];
        colors = new int[G.V()];
        for (int s = 0; s < G.V(); s++)
            if (!marked[s])
            {
                dfs(G, s, 1);
                count++;
            }
    }
    private void dfs(Graph G, int v, int color)
    {
        marked[v] = true;
        id[v] = count;
        colors[v] = color;
        for (int w : G.adj(v))
            if (!marked[w]) {
                dfs(G, w, color == 1 ? 2 : 1);
            }
            else {
                if (w == v) {
                    acylic = false;
                }

                if (colors[w] == colors[v]) {
                    bypartite = false;
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

    public static void main(String[] args)
    {
        Graph G = new Graph(new In(CC.class.getResource("/testG.txt")));
        CC cc = new CC(G);
        int M = cc.count();
        System.out.println("acylic=" + cc.isAcylic());
        System.out.println("bypartite=" + cc.isBypartite());

        StdOut.println(M + " components");
        Bag<Integer>[] components;
        components = (Bag<Integer>[]) new Bag[M];
        for (int i = 0; i < M; i++)
            components[i] = new Bag<>();
        for (int v = 0; v < G.V(); v++)
            components[cc.id(v)].add(v);
        for (int i = 0; i < M; i++)
        {
            for (int v: components[i])
                StdOut.print(v + " ");
            StdOut.println();
        }
    }
}