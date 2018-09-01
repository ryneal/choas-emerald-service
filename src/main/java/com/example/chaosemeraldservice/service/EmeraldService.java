package com.example.chaosemeraldservice.service;

import com.example.chaosemeraldservice.exception.EmeraldException;
import com.example.chaosemeraldservice.model.Colour;
import com.example.chaosemeraldservice.model.Emerald;

import java.util.List;

public interface EmeraldService {

    Emerald getEmerald(Long id) throws EmeraldException;

    List<Emerald> getEmeralds() throws EmeraldException;

    Emerald updateEmerald(Long id, Emerald emerald) throws EmeraldException;

    Emerald createEmerald(Long powerLevel, Colour colour) throws EmeraldException;

    void deleteEmerald(Long id) throws EmeraldException;
}
