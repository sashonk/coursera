package ru.asocial.coursera.dto;

public class EdgeDTO {
    private Integer from;
    private Integer to;

    public EdgeDTO(Integer from, Integer to) {
        this.from = from;
        this.to = to;
    }

    public String getId() {
        return from+"-"+to;
    }

    public Integer getFrom() {
        return from;
    }

    public Integer getTo() {
        return to;
    }

}
