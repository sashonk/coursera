package ru.asocial.coursera.events;

public class EdgeToEvent implements Event {
    private Integer v;
    private Integer w;

    public EdgeToEvent(Integer w, Integer v) {
        this.v = v;
        this.w = w;
    }

    public Integer getW() {
        return w;
    }

    public Integer getV() {
        return v;
    }

    @Override
    public String getType() {
        return "edgeTo";
    }
}
