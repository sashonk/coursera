package ru.asocial.coursera.events;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class PathToEvent implements Event {

    private List<Integer> pathTo;

    @Override
    public String getType() {
        return "pathTo";
    }

    public List<Integer> getPathTo() {
        return pathTo;
    }

    public PathToEvent(Iterable<Integer> path) {
        pathTo = new LinkedList<>();
        Iterator<Integer> it = path.iterator();
        while (it.hasNext()) {
            pathTo.add(it.next());
        }
    }
}
