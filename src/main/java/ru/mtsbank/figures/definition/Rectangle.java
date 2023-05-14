package ru.mtsbank.figures.definition;

public class Rectangle extends GeometricFigure {
    private double width;
    private double height;

    public Rectangle(String color, int id, double width, double height) {
        super(color, id);
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
