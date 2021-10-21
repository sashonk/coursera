package ru.asocial.coursera;

public class VerticeDTO {

    private Integer id;

    private String label;

    public String getLabel() {
        return label;
    }

    public VerticeDTO(Integer id, String label) {
        this.id = id;
        this.label = label;
    }

    public Integer getId() {
        return id;
    }
}
