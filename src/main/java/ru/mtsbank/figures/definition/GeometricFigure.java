package ru.mtsbank.figures.definition;

public class GeometricFigure {
    private String color;
    private int id;

    public GeometricFigure(String color, int id) {
        this.color = color;
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public int getId() {
        return id;
    }
}
