package ru.mtsbank.figures;

import jakarta.persistence.*;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "color", schema = "public")
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name", nullable = false, length = 20)
    @NotNull
    @Size(min = 2)
    @Size(max = 20)
    private String name;
    @Column(name = "red_code", nullable = false)
    @NotNull
    @Min(value = 0)
    @Max(value = 255)
    private Integer redCode;
    @Column(name = "green_code", nullable = false)
    @NotNull
    @Min(value = 0)
    @Max(value = 255)
    private Integer greenCode;
    @Column(name = "blue_code", nullable = false)
    @NotNull
    @Min(value= 0)
    @Max(value = 255)
    private Integer blueCode;

    @OneToMany(targetEntity = Figure.class, mappedBy = "color")
    private List<@Valid Figure> figure;

    public Color() {}
    public Color(@NotNull String name, int redCode, int greenCode, int blueCode) {
        this.name = name;
        this.redCode = redCode;
        this.greenCode = greenCode;
        this.blueCode = blueCode;
    }

    public @NotNull String getName() {
        return name;
    }

    public int getRedCode() { return redCode; }

    public int getGreenCode() {
        return greenCode;
    }

    public int getBlueCode() {
        return blueCode;
    }

}

