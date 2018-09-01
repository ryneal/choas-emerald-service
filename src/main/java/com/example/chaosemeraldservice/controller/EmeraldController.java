package com.example.chaosemeraldservice.controller;

import com.example.chaosemeraldservice.exception.EmeraldCreationFailedException;
import com.example.chaosemeraldservice.exception.EmeraldException;
import com.example.chaosemeraldservice.exception.EmeraldUpdateFailedException;
import com.example.chaosemeraldservice.model.Emerald;
import com.example.chaosemeraldservice.model.EmeraldDto;
import com.example.chaosemeraldservice.service.EmeraldService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EmeraldController {

    private static final String EMERALD_URI = "/emeralds";

    private EmeraldService emeraldService;

    public EmeraldController(EmeraldService emeraldService) {
        this.emeraldService = emeraldService;
    }

    @GetMapping(value = EMERALD_URI + "/{id}")
    public ResponseEntity<Emerald> getEmerald(@PathVariable Long id) throws EmeraldException {
        return ResponseEntity.ok(Optional.ofNullable(this.emeraldService.getEmerald(id))
                .orElseThrow(EmeraldException::new));
    }

    @GetMapping(value = EMERALD_URI)
    public ResponseEntity<List<Emerald>> getAllEmeralds() throws EmeraldException {
        return ResponseEntity.ok(Optional.ofNullable(this.emeraldService.getEmeralds())
                .orElseThrow(EmeraldException::new));
    }

    @PostMapping(value = EMERALD_URI)
    public ResponseEntity<Emerald> postEmerald(@RequestBody EmeraldDto emerald) throws EmeraldException {
        Emerald createdEmerald = Optional.ofNullable(this.emeraldService.createEmerald(
                emerald.getPowerLevel(), emerald.getColour()))
                .orElseThrow(EmeraldCreationFailedException::new);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmerald);
    }

    @PutMapping(value = EMERALD_URI + "/{id}")
    public ResponseEntity<Emerald> putEmerald(@PathVariable Long id, @RequestBody EmeraldDto emerald) throws EmeraldException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Optional.ofNullable(this.emeraldService
                        .updateEmerald(id, new Emerald(emerald.getPowerLevel(), emerald.getColour())))
                        .orElseThrow(EmeraldUpdateFailedException::new));
    }

    @DeleteMapping(value = EMERALD_URI + "/{id}")
    public ResponseEntity deleteEmerald(@PathVariable Long id) throws EmeraldException {
        this.emeraldService.deleteEmerald(id);
        return ResponseEntity.noContent().build();
    }
}
