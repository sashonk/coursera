package ru.asocial.coursera.dto;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Graph;

import java.util.LinkedList;
import java.util.List;

public class GraphDTO {
    private List<VerticeDTO> vertices = new LinkedList<>();
    private List<EdgeDTO> edges = new LinkedList<>();

    private void addVertice(Integer i) {
        vertices.add(new VerticeDTO(i, String.valueOf(i)));
    }

    private void addEdge(Integer from, Integer to) {
        edges.add(new EdgeDTO(from, to));
    }

    public List<VerticeDTO> getVertices() {
        return vertices;
    }

    public List<EdgeDTO> getEdges() {
        return edges;
    }

    public static GraphDTO convert(Graph graph) {
        GraphDTO result = new GraphDTO();

        for (int i = 0; i < graph.V(); i++) {
            result.addVertice(i );
            for (int j : graph.adj(i)) {
                result.addEdge(i, j );
            }
        }
        return result;
    }

    public static GraphDTO convert(Digraph graph) {
        GraphDTO result = new GraphDTO();

        for (int i = 0; i < graph.V(); i++) {
            result.addVertice(i );
            for (int j : graph.adj(i)) {
                result.addEdge(i , j);
            }
        }
        return result;
    }
}
