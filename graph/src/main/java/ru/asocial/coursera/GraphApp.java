package ru.asocial.coursera;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import ru.asocial.coursera.dto.GraphDTO;
import ru.asocial.coursera.dto.HasPathTo;
import ru.asocial.coursera.events.EdgeToEvent;
import ru.asocial.coursera.events.Event;
import ru.asocial.coursera.events.MarkedEvent;

import javax.servlet.http.HttpServletRequest;

@RestController
@SpringBootApplication
public class GraphApp {

    private static final String ATTR_GRAPH = "graph";
    private static final String ATTR_EVENT_QUEUE = "eventQueue";
    private static final String ATTR_DFS = "dfs";

    @RequestMapping(value = "/graph/graph", method = RequestMethod.POST, produces = "application/json")
    GraphDTO graph(@RequestParam String type, @RequestParam Integer vertices, @RequestParam Integer edges, HttpServletRequest request) {
        Graph graph = Graphs.createGraph(type, vertices, edges);
        request.getSession().setAttribute(ATTR_GRAPH, graph);
        return GraphDTO.convert(graph) ;
    }

    @RequestMapping(value = "/graph/digraph", method = RequestMethod.POST, produces = "application/json")
    GraphDTO digraph(@RequestParam String type, @RequestParam Integer vertices, @RequestParam Integer edges, HttpServletRequest request) {
        Digraph digraph = Graphs.createDigraph(type, vertices, edges);
        request.getSession().setAttribute(ATTR_GRAPH, digraph);
        return GraphDTO.convert(digraph) ;
    }

    @RequestMapping(value = "/graph/dfs", method = RequestMethod.POST, produces = "application/json")
    void dfs(@RequestParam Integer source, HttpServletRequest request) {
         Object graph = request.getSession().getAttribute(ATTR_GRAPH);
         if (graph instanceof Digraph) {
             Queue<Event> eventList = new Queue<>();
             request.getSession().setAttribute(ATTR_EVENT_QUEUE, eventList);
             DepthFirstDirectedPaths paths = new DepthFirstDirectedPaths((Digraph) graph, source, new EventListener() {
                 @Override
                 public void marked(int v) {
                     eventList.enqueue(new MarkedEvent(v));
                 }

                 @Override
                 public void edgeTo(int w, int v) {
                     eventList.enqueue(new EdgeToEvent(w, v));
                 }
             });
             request.getSession().setAttribute(ATTR_DFS, paths);
         }
    }

    @RequestMapping(value = "/graph/hasPathTo", method = RequestMethod.GET, produces = "application/json")
    public HasPathTo hasPathTo(@RequestParam Integer v, HttpServletRequest request) {
        DepthFirstDirectedPaths paths = (DepthFirstDirectedPaths) request.getSession().getAttribute(ATTR_DFS);
        if (paths == null) {
            throw new IllegalArgumentException("run DFS first");
        }

        boolean hasPathTo = paths.hasPathTo(v);
        HasPathTo result = new HasPathTo();
        result.setHasPath(hasPathTo);
        if (hasPathTo) {
            result.setPath(paths.pathTo(v));
        }
        return result;
    }

    @RequestMapping(value = "/graph/getEvent", method = RequestMethod.PUT, produces = "application/json")
    Event getEvent(HttpServletRequest request) {
        Queue<Event> events = (Queue<Event>) request.getSession().getAttribute(ATTR_EVENT_QUEUE);
        if (!events.isEmpty()) {
            return events.dequeue();
        }
        return null;
    }

    public static void main(String[] argc) {
        SpringApplication.run(GraphApp.class);
    }
}
