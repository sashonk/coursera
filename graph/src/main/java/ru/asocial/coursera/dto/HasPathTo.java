package ru.asocial.coursera.dto;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class HasPathTo {
    private boolean hasPath;
    private List<Integer> path;

    public boolean isHasPath() {
        return hasPath;
    }

    public void setHasPath(boolean hasPath) {
        this.hasPath = hasPath;
    }

    public List<Integer> getPath() {
        return path;
    }

    public void setPath(Iterable<Integer> p) {
        Iterator<Integer> it = p.iterator();
        this.path = new LinkedList<>();
        while (it.hasNext()) {
            this.path.add(it.next());
        }
    }
}
