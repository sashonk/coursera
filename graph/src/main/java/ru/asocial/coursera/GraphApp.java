package ru.asocial.coursera;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController()
@SpringBootApplication
public class GraphApp {

    private static final String ATTR_GRAPH = "graph";
    private static final String ATTR_EVENT_QUEUE = "eventQueue";

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
    Map<String, Object> dfs(@RequestParam Integer source, @RequestParam Integer to, HttpServletRequest request) {
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

             Map<String, Object> result = new HashMap<>();
             result.put("hasPath", paths.hasPathTo(to));
             eventList.enqueue(new PathToEvent(paths.pathTo(to)));
             return result;
         }
         return Collections.emptyMap();
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
