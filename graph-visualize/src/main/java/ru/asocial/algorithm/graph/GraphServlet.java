package ru.asocial.algorithm.graph;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DigraphGenerator;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.GraphGenerator;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GraphServlet extends HttpServlet {

    private static final String ATTR_GRAPH = "graph";
    private static final String ATTR_CC = "cc";

    @Override
    public void init(){
        System.out.println("Graph servlet initialized");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        Graph graph = (Graph) request.getSession().getAttribute(ATTR_GRAPH);
        try {
            if (graph != null) {
                JSONObject json = new JSONObject();
                JSONArray vertices = new JSONArray();
                JSONArray edges = new JSONArray();
                json.put("edges", edges);
                json.put("vertices", vertices);
                for (int i = 0; i<graph.V(); i++){
                    JSONObject vertice = new JSONObject();
                    vertice.put("id", i + 1);
                    vertice.put("label", String.valueOf(i + 1));
                    vertices.put(vertice);
                    for (int j : graph.adj(i)) {
                        JSONObject edge = new JSONObject();
                        edge.put("from", i + 1);
                        edge.put("to", j + 1);
                        edges.put(edge);
                    }
                }

                response.getWriter().println(json.toString());
                response.getWriter().flush();
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        Graph graph = (Graph) request.getSession().getAttribute(ATTR_GRAPH);
        if (graph == null) {
            throw new IllegalArgumentException("create graph first");
        }

        CC cc = (CC) request.getSession().getAttribute(ATTR_CC);
        if (cc == null) {
            throw new IllegalArgumentException("component counter missing");
        }

        try {
            Integer vertice = cc.getNext();
            JSONObject o = new JSONObject();
            if (vertice == null) {
                o.put("complete", true);
            }
            else {
                o.put("vertice", vertice + 1);
            }
            response.getWriter().println(o.toString());
            response.getWriter().flush();
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        Graph graph = createGraph(request);
        request.getSession().setAttribute(ATTR_GRAPH, graph);
        CC cc = new CC(graph);
        cc.start();
        request.getSession().setAttribute(ATTR_CC, cc);

        doGet(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("Graph servlet destroy");
    }

    private Graph createGraph(HttpServletRequest request) {
        String type = request.getParameter("type");
        int vertices = request.getParameter("vertices") != null ? Integer.parseInt(request.getParameter("vertices")) : 100;
        int edges = request.getParameter("edges") != null ? Integer.parseInt(request.getParameter("edges")) : 50;
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
        return GraphGenerator.path(vertices);
    }
}
