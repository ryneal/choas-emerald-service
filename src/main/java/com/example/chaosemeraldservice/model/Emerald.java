package com.example.chaosemeraldservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Emerald {

    @Id
    @GeneratedValue
    private Long id;

    private Long powerLevel;

    private Colour colour;

    public Emerald(Long powerLevel, Colour colour) {
        this.powerLevel = powerLevel;
        this.colour = colour;
    }

    public Long getId() {
        return id;
    }

    public Long getPowerLevel() {
        return powerLevel;
    }

    public Colour getColour() {
        return colour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Emerald emerald = (Emerald) o;
        return Objects.equals(id, emerald.id) &&
                Objects.equals(powerLevel, emerald.powerLevel) &&
                colour == emerald.colour;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, powerLevel, colour);
    }
}
