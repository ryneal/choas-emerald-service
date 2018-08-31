package com.example.chaosemeraldservice.service.impl;

import com.example.chaosemeraldservice.exception.*;
import com.example.chaosemeraldservice.model.Colour;
import com.example.chaosemeraldservice.model.Emerald;
import com.example.chaosemeraldservice.persistence.EmeraldRepository;
import com.example.chaosemeraldservice.service.EmeraldService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmeraldServiceImpl implements EmeraldService {

    private EmeraldRepository emeraldRepository;

    public EmeraldServiceImpl(EmeraldRepository emeraldRepository) {
        this.emeraldRepository = emeraldRepository;
    }

    @Override
    public Emerald getEmerald(Long id) throws EmeraldException {
        return this.emeraldRepository.findById(id).orElseThrow(EmeraldNotFoundException::new);
    }

    @Override
    public List<Emerald> getEmeralds() {
        return (List<Emerald>) this.emeraldRepository.findAll();
    }

    @Override
    public Emerald updateEmerald(Emerald emerald) throws EmeraldException {
        Emerald existingEmerald = getEmerald(emerald.getId());
        if (existingEmerald.equals(emerald)) {
            throw new EmeraldNotUpdatedException();
        }
        return Optional.ofNullable(this.emeraldRepository.save(emerald))
                .orElseThrow(EmeraldNotUpdatedException::new);
    }

    @Override
    public Emerald createEmerald(Long powerLevel, Colour colour) throws EmeraldException {
        return Optional.ofNullable(this.emeraldRepository.save(new Emerald(powerLevel, colour)))
                .orElseThrow(EmeraldCreationFailedException::new);
    }

    @Override
    public void deleteEmerald(Long id) throws EmeraldException {
        this.emeraldRepository.deleteById(id);
        if (this.emeraldRepository.findById(id).isPresent()) {
            throw new EmeraldDeletionFailedException();
        }
    }
}
