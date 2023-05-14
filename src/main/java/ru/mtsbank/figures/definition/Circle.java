package ru.mtsbank.figures.definition;

public class Circle extends GeometricFigure {
    private double radius;

    public Circle(String color, int id, double radius) {
        super(color, id);
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }
}
