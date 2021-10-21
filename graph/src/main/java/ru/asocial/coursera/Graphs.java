package ru.asocial.coursera;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DigraphGenerator;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.GraphGenerator;

class Graphs {

    static Digraph createDigraph(String type, int vertices, int edges) {
        if ("(digraph)binaryTree".equals(type)) {
            return DigraphGenerator.binaryTree(vertices);
        }
        else if ("(digraph)complete".equals(type)) {
            return DigraphGenerator.complete(vertices);
        }
        else if ("(digraph)cycle".equals(type)) {
            return DigraphGenerator.cycle(vertices);
        }
        else if ("(digraph)tournament".equals(type)) {
            return DigraphGenerator.tournament(vertices);
        }
        else if ("(digraph)eulerianPath".equals(type)) {
            return DigraphGenerator.eulerianPath(vertices, edges);
        }
        else if ("(digraph)simple".equals(type)) {
            return DigraphGenerator.simple(vertices, edges);
        }
        else if ("(digraph)rootedInDAG".equals(type)) {
            return DigraphGenerator.rootedInDAG(vertices, edges);
        }
        else if ("(digraph)rootedOutDAG".equals(type)) {
            return DigraphGenerator.rootedOutDAG(vertices, edges);
        }
        else if ("(digraph)rootedInTree".equals(type)) {
            return DigraphGenerator.rootedInTree(vertices);
        }
        else if ("(digraph)rootedOutTree".equals(type)) {
            return DigraphGenerator.rootedOutTree(vertices);
        }
        else if ("(digraph)dag".equals(type)) {
            return DigraphGenerator.dag(vertices, edges);
        }
        else if ("(digraph)path".equals(type)) {
            return DigraphGenerator.path(vertices);
        }

        throw new IllegalArgumentException("graph type required");
    }

    static Graph createGraph(String type, int vertices, int edges) {
            if ("binaryTree".equals(type)) {
                return GraphGenerator.binaryTree(vertices);
            }
            else if ("complete".equals(type)) {
                return GraphGenerator.complete(vertices);
            }
            else if ("cycle".equals(type)) {
                return GraphGenerator.cycle(vertices);
            }
            else if ("tree".equals(type)) {
                return GraphGenerator.tree(vertices);
            }
            else if ("eulerian".equals(type)) {
                return GraphGenerator.eulerianPath(vertices, edges);
            }
            else if ("simple".equals(type)) {
                return GraphGenerator.simple(vertices, edges);
            }
            else if ("star".equals(type)) {
                return GraphGenerator.star(vertices);
            }
            else if ("wheel".equals(type)) {
                return GraphGenerator.wheel(vertices);
            }
            else if ("path".equals(type)) {
                return GraphGenerator.path(vertices);
            }

            throw new IllegalArgumentException("graph type required");
    }
}
