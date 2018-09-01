package com.example.chaosemeraldservice.model;

public class EmeraldDto {
    private Long powerLevel;
    private Colour colour;

    protected EmeraldDto() {
    }

    public EmeraldDto(Long powerLevel, Colour colour) {
        this.powerLevel = powerLevel;
        this.colour = colour;
    }

    public Long getPowerLevel() {
        return powerLevel;
    }

    public Colour getColour() {
        return colour;
    }
}
