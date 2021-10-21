package ru.asocial.coursera;

public interface EventListener {
    void marked(int v);

    void edgeTo(int w, int v);
}
