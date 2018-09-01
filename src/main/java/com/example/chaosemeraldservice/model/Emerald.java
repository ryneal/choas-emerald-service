package com.example.chaosemeraldservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Emerald {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long powerLevel;

    private Colour colour;

    protected Emerald() {
    }

    public Emerald(Long powerLevel, Colour colour) {
        this.powerLevel = powerLevel;
        this.colour = colour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Emerald emerald = (Emerald) o;
        return Objects.equals(this.getPowerLevel(), emerald.getPowerLevel()) &&
                this.getColour() == emerald.getColour();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.powerLevel, this.colour);
    }

    public Long getId() {
        return id;
    }

    public Long getPowerLevel() {
        return powerLevel;
    }

    public void setPowerLevel(Long powerLevel) {
        this.powerLevel = powerLevel;
    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }
}
