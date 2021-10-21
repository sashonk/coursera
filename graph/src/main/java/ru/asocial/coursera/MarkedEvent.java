package ru.asocial.coursera;

public class MarkedEvent implements Event {

    private Integer v;

    public MarkedEvent(Integer v) {
        this.v = v;
    }

    public Integer getV() {
        return v;
    }

    @Override
    public String getType() {
        return "marked";
    }
}
