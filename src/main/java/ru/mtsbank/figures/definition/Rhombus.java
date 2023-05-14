package ru.mtsbank.figures.definition;

public class Rhombus extends GeometricFigure {
    private double sideLength;

    public Rhombus(String color, int id, double sideLength) {
        super(color, id);
        this.sideLength = sideLength;
    }

    public double getSideLength() {
        return sideLength;
    }
}
