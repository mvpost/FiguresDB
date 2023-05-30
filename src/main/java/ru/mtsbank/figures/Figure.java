package ru.mtsbank.figures;

import jakarta.persistence.*;

@Entity
@Table(name="figure", schema="public")
public class Figure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    private int id;
    @Column(name = "type", nullable = true, length = 20)
    private String type;
    @ManyToOne
    private Color color;

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Figure() {}
    public Figure(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType( String type ) {
        this.type = type;
    }

}

