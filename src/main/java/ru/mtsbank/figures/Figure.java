package ru.mtsbank.figures;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="figure", schema="public")
public class Figure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "type", nullable = false, length = 20)
    @NotEmpty
    @Size(max = 20)
    private String type;
    @ManyToOne()
    @JoinColumn(name="color_id", referencedColumnName="id", columnDefinition="integer", nullable=false)
    private Color color;
    public Color getColor() { return this.color; }

    public void setColor(Color color) { this.color = color; }

    public Figure() {}

    public Figure(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}

